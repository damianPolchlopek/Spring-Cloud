package org.example.photoappapiusers.ui.response;

import org.example.photoappapiusers.shared.UserDto;

import java.util.List;

public record UserResponseModel(
        String userId,
        String firstName,
        String lastName,
        String email,
        List<AlbumResponseModel> albums
) {
    public static UserResponseModel of(UserDto userDto) {
        return new UserResponseModel(
                userDto.userId(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.email(),
                userDto.albumsList()
        );
    }
}
