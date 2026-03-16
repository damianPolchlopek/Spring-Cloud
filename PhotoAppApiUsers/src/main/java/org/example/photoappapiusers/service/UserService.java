package org.example.photoappapiusers.service;

import lombok.RequiredArgsConstructor;
import org.example.photoappapiusers.data.UserEntity;
import org.example.photoappapiusers.data.UserRepository;
import org.example.photoappapiusers.shared.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserDto createUser(UserDto userDetails){

        final String userId = UUID.randomUUID().toString();
        final String bCryptPassword = bCryptPasswordEncoder.encode(userDetails.password());

        UserEntity userEntity = new UserEntity(userDetails);
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword(bCryptPassword);

        UserEntity savedUser = userRepository.save(userEntity);

        return UserDto.of(savedUser);
    }

}
