package org.example.photoappapiusers.ui.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.photoappapiusers.service.UserService;
import org.example.photoappapiusers.shared.UserDto;
import org.example.photoappapiusers.ui.request.CreateUserRequest;
import org.example.photoappapiusers.ui.response.CreateUserResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    @Value("${app.message:default}")
    private String message;

    private final UserService userService;
    private final Environment environment;

    @GetMapping("/status/check")
    public String check(HttpServletRequest request) {
        return "OK from port: " + environment.getProperty("local.server.port")
                + ", caller IP: " + request.getRemoteAddr() + ", config msg: " + message;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {

        UserDto userDto = new UserDto(createUserRequest);
        UserDto user = userService.createUser(userDto);

        CreateUserResponseModel returnValue = CreateUserResponseModel.of(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

}
