package com.wendellemos.webfluxcourse.model.request;

import com.wendellemos.webfluxcourse.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @TrimString
        @NotBlank(message = "Name cannot be null or empty")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name,
        @TrimString
        @NotBlank(message = "Email cannot be null or empty")
        @Email(message = "Insert a valid e-mail")
        String email,
        @TrimString
        @NotBlank(message = "Password cannot be null or empty")
        @Size(min = 6, max = 20, message = "Password must contain between 6 and 20 characters")
        String password
) {
}
