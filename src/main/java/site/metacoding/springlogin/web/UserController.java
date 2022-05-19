package site.metacoding.springlogin.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.springlogin.config.auth.LoginUser;
import site.metacoding.springlogin.domain.User;
import site.metacoding.springlogin.handler.ex.CustomException;
import site.metacoding.springlogin.service.UserService;
import site.metacoding.springlogin.web.dto.JoinReqDto;
import site.metacoding.springlogin.web.dto.UpdateReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 로그인 성공 시 메인 페이지 폼
    @GetMapping("/s/user/{id}")
    public String main() {
        return "/main";
    }

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
    @GetMapping("/s/user/{id}/update-form")
    public String updateForm(Model model, @AuthenticationPrincipal LoginUser loginUser) {

        User userEntity = userService.회원정보보기(loginUser.getUser());
        model.addAttribute("user", userEntity);
        return "/updateForm";
    }

    // 회원 정보 수정
    @PutMapping("/s/api/user/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateReqDto updateReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        userService.회원수정(loginUser.getUser(), updateReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원가입 시 유저아이디 중복체크
    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> usernameSameCheck(String username) {
        boolean isNotSame = userService.유저아이디중복체크(username); // true (같지 않다)
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }
}
