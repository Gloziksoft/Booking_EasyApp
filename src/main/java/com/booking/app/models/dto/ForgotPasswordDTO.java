package com.booking.app.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDTO {

    @NotBlank(message = "Email musí byť zadaný.")
    @Email(message = "Zadajte platnú e-mailovú adresu.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Zadajte platnú e-mailovú adresu."
    )
    @Size(max = 254, message = "Email môže mať maximálne 254 znakov.")
    private String email;
}