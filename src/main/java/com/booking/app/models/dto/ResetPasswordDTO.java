package com.booking.app.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    @NotBlank(message = "Token je povinný.")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "Token má neplatný formát."
    )
    private String token;

    @NotBlank(message = "Heslo je povinné.")
    @Size(min = 8, max = 72, message = "Heslo musí mať 8 až 72 znakov.")
    private String password;

    @NotBlank(message = "Potvrdenie hesla je povinné.")
    @Size(min = 8, max = 72, message = "Potvrdené heslo musí mať 8 až 72 znakov.")
    private String confirmPassword;
}