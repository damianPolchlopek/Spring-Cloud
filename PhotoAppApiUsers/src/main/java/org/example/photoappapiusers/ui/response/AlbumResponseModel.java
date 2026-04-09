package org.example.photoappapiusers.ui.response;

public record AlbumResponseModel(
        String albumId,
        String userId,
        String name,
        String description
) {
}
