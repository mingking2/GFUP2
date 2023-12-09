package com.example.gfup2.web.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@Tag(name = "MemberController", description = "사용자의 회원정보 변경과 관련된 컨트롤러")
public class MemberEditController {

    @GetMapping("/mingibabo")
    public void test(){
        System.out.println("hi");
    }

}
