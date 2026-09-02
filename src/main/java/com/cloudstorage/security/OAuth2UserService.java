package com.cloudstorage.security;

import com.cloudstorage.model.User;
import com.cloudstorage.model.enums.UserRole;
import com.cloudstorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Handles the OAuth2 user info response from Google.
 *
 * Flow:
 *  1. User authenticates with Google.
 *  2. Spring fetches user info from Google's userinfo endpoint.
 *  3. This service finds or creates the local user record.
 *  4. The result is used by the OAuth2 success handler to generate a JWT.
 *
 * Wired into SecurityConfig when Google credentials are configured.
 * Phase 2 stub — full OAuth2 redirect + JWT issuance added in Phase 4.
 */
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger log = LoggerFactory.getLogger(OAuth2UserService.class);

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email      = oAuth2User.getAttribute("email");
        String name       = oAuth2User.getAttribute("name");
        String providerId = oAuth2User.getAttribute("sub"); // Google's unique user ID

        if (email == null) {
            throw new OAuth2AuthenticationException("Email not provided by OAuth2 provider");
        }

        // Find existing user or create a new one
        User user = userRepository.findByEmail(email).orElseGet(() -> {
            log.info("Creating new OAuth2 user: {}", email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name != null ? name : email);
            newUser.setProvider("GOOGLE");
            newUser.setProviderId(providerId);
            newUser.setRole(UserRole.USER);
            // No password hash for OAuth2 users
            return userRepository.save(newUser);
        });

        // Update providerId if missing (e.g. user previously registered with email)
        if (user.getProviderId() == null && providerId != null) {
            user.setProviderId(providerId);
            userRepository.save(user);
        }

        return oAuth2User;
    }
}
