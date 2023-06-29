package com.wendellemos.webfluxcourse.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Name cannot be null or empty")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name,
        @NotBlank(message = "Email cannot be null or empty")
        @Email(message = "Insert a valid e-mail")
        String email,
        @NotBlank(message = "Password cannot be null or empty")
        @Size(min = 6, max = 20, message = "Password must contain between 6 and 20 characters")
        String password
) {
}
