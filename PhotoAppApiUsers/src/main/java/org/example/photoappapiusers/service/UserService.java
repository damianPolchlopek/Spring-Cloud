package org.example.photoappapiusers.service;

import lombok.RequiredArgsConstructor;
import org.example.photoappapiusers.data.AlbumsServiceClient;
import org.example.photoappapiusers.data.UserEntity;
import org.example.photoappapiusers.data.UserRepository;
import org.example.photoappapiusers.shared.UserDto;
import org.example.photoappapiusers.ui.response.AlbumResponseModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RestTemplate restTemplate;
    private final Environment environment;
    private final AlbumsServiceClient albumsServiceClient;

    public UserDto createUser(UserDto userDetails){

        final String userId = UUID.randomUUID().toString();
        final String bCryptPassword = bCryptPasswordEncoder.encode(userDetails.password());

        UserEntity userEntity = new UserEntity(userDetails);
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(bCryptPassword);

        UserEntity savedUser = userRepository.save(userEntity);

        return UserDto.of(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(user.getEmail(), user.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }

    public UserDto getUserByEmail(String email){
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return UserDto.of(user);
    }

    public UserDto getUserByUserId(String userId) {
        UserEntity user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }

        UserDto userDto = UserDto.of(user);

//        String albumsUrl = "http://localhost:52388/users/%s/albums";
//        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
//        ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
//                restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {});
//        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();

        List<AlbumResponseModel> albums = albumsServiceClient.getAlbums(userId);

        return userDto.setAlbumsList(albums);
    }
}
