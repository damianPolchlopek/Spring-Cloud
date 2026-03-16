package org.example.photoappapiusers.ui.response;

import org.example.photoappapiusers.shared.UserDto;

public record CreateUserResponseModel(
        String firstName,
        String lastName,
        String email,
        String userId
) {
    public static CreateUserResponseModel of(UserDto user) {
        return new CreateUserResponseModel(
                user.firstName(),
                user.lastName(),
                user.email(),
                user.userId()
        );
    }
}
