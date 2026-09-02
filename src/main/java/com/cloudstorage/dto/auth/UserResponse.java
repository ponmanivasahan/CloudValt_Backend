package com.cloudstorage.dto.auth;

import com.cloudstorage.model.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UserResponse {

    private UUID   id;
    private String name;
    private String email;
    private UserRole role;
    private String provider;
}
