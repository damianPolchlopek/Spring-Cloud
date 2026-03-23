package org.example.photoappapiacountmanagement.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/account")
public class AccountController {

    @Value("${app.message:default}")
    private String message;

    @GetMapping("/status/check")
    public String check() {
        return "Working " + message;
    }

}
