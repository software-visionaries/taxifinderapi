package app.taxifinderapi.dto;


public record UserRegistrationDto(
        String email,
        String name,
        String password,
        String role,
        String pushToken
) {}
