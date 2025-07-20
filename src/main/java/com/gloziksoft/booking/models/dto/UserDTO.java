package com.gloziksoft.booking.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "Meno je povinné")
    private String firstName;

    @NotBlank(message = "Priezvisko je povinné")
    private String lastName;

    @Email(message = "Zadaj platný e-mail")
    @NotBlank(message = "Email je povinný")
    private String email;

    @NotBlank(message = "Heslo je povinné")
    private String password;

    @NotBlank(message = "Potvrdenie hesla je povinné")
    private String confirmPassword;
}

