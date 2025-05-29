package dev.alexgiou.authservice.dto;

import lombok.Data;

/**
 * @author: Alexandros Giounan
 * @code @created: 5/28/2025
 */

@Data
public class LoginResponse {
    private final String token;
}
