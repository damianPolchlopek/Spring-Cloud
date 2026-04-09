package org.example.photoappapiusers.shared;

import jakarta.validation.Valid;
import org.example.photoappapiusers.data.UserEntity;
import org.example.photoappapiusers.ui.request.CreateUserRequest;
import org.example.photoappapiusers.ui.response.AlbumResponseModel;

import java.util.List;

public record UserDto(
        String firstName,
        String lastName,
        String password,
        String email,
        String userId,
        String encryptedPassword,
        List<AlbumResponseModel> albumsList
) {
    public UserDto(CreateUserRequest createUserRequest) {
        this(
            createUserRequest.firstName(),
            createUserRequest.lastName(),
            createUserRequest.password(),
            createUserRequest.email(),
            null,
            null,
                null
        );
    }

    public static UserDto of(UserEntity user) {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                null,
                user.getEmail(),
                user.getUserId(),
                user.getEncryptedPassword(),
                null
        );
    }

    public UserDto setAlbumsList(List<AlbumResponseModel> albumsList) {
        return new UserDto(
                firstName,
                lastName,
                password,
                email,
                userId,
                encryptedPassword,
                albumsList
        );
    }
}
