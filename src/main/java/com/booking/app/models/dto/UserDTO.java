package com.booking.app.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Heslo je povinné.")
    @Size(min = 1, message = "Heslo musí mať aspoň 6 znakov.")
    private String password;

    @NotBlank(message = "Potvrdenie hesla je povinné.")
    @Size(min = 1, message = "Potvrdené heslo musí mať aspoň 6 znakov.")
    private String confirmPassword;

}
