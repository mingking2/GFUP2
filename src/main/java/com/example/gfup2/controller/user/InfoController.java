package com.example.gfup2.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class InfoController {

    @GetMapping("/profile")
    public void showProfile() {

    }

    @PostMapping("/profileUpload")
    public void uploadProfile() {

    }

    @PostMapping("/changePhoneNum")
    public void changePhoneNumber() {

    }
}
