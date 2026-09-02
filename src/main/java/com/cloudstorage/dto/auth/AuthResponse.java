package com.cloudstorage.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String       token;
    private UserResponse user;
}
