package org.example.photoappapiusers.service;

import lombok.RequiredArgsConstructor;
import org.example.photoappapiusers.data.UserEntity;
import org.example.photoappapiusers.data.UserRepository;
import org.example.photoappapiusers.shared.UserDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

}
