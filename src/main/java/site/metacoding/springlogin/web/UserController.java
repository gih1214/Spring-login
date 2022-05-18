package site.metacoding.springlogin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    // 회원가입 폼
    @GetMapping("/join-form")
    public String joinForm() {
        return "/joinForm";
    }

    // 로그인 폼
    @GetMapping("/login-form")
    public String loginForm() {
        return "/loginForm";
    }

    // 회원수정 폼
    @GetMapping("/update-form")
    public String updateForm() {
        return "/updateForm";
    }
}
