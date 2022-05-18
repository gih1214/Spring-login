package site.metacoding.springlogin.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.springlogin.handler.ex.CustomException;
import site.metacoding.springlogin.service.UserService;
import site.metacoding.springlogin.web.dto.JoinReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 회원가입 폼
    @GetMapping("/join-form")
    public String joinForm() {
        return "/joinForm";
    }

    // 회원가입하기
    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new CustomException(errorMap.toString());
        }

        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
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

    // 회원가입 시 유저아이디 중복체크
    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> userIdSameCheck(String username) {
        boolean isNotSame = userService.유저아이디중복체크(username); // true (같지 않다)
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }

    // 회원가입 시 이메일 중복체크
    @GetMapping("/api/user/email-same-check")
    public ResponseEntity<?> emailSameCheck(String email) {
        boolean isNotSame = userService.이메일중복체크(email); // true (같지 않다)
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }
}
