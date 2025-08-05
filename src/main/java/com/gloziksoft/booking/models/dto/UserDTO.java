package com.gloziksoft.booking.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for User.
 * Used to transfer user data between application layers.
 * Includes validation annotations to ensure required fields and valid formats.
 */
@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "Meno musí byť zadané.")
    private String firstName;

    @NotBlank(message = "Priezvisko musí byť zadané.")
    private String lastName;

    @Email(message = "Zadajte platnú e-mailovú adresu.")
    @NotBlank(message = "Email musí byť zadaný.")
    private String email;

    @NotBlank(message = "Heslo musí byť zadamé.")
    private String password;

    @NotBlank(message = "Potvrdenie hesla musí byť zadané.")
    private String confirmPassword;
}
