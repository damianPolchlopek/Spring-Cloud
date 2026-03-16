package org.example.photoappapiusers.ui.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotNull(message = "First name can not be null")
        @Size(min=2, message = "First name must not be less than two characters")
        String firstName,

        @NotNull
        @Size(min = 2, message = "Last name must not be less than two characters")
        String lastName,

        @NotNull(message = "Password cannot be null")
        @Size(min = 8, max = 16, message = "Password must be equal or greater than 8 characters and less than 16 characters")
        String password,

        @NotNull(message = "Email cannot be null")
        @Email
        String email
) {
}
