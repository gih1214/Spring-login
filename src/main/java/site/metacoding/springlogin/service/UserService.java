package site.metacoding.springlogin.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.springlogin.domain.User;
import site.metacoding.springlogin.domain.UserRepository;
import site.metacoding.springlogin.handler.ex.CustomApiException;
import site.metacoding.springlogin.handler.ex.CustomException;
import site.metacoding.springlogin.web.dto.UpdateReqDto;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입하기
    @Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword(); // 1234
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해시 알고리즘
        user.setPassword(encPassword);

        userRepository.save(user);
    }

    // 회원정보 보기
    public User 회원정보보기(User principal) {

        Optional<User> userOp = userRepository.findById(principal.getId());
        if (userOp.isPresent()) {
            return userOp.get();
        } else {
            throw new CustomException("해당 유저가 존재하지 않습니다");
        }
    }

    // 회원정보 수정하기
    @Transactional
    public void 회원수정(User principal, UpdateReqDto updateReqDto) {

        String rawPassword = principal.getPassword(); // 1234
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 해시 알고리즘
        updateReqDto.setPassword(encPassword);

        Optional<User> userOp = userRepository.findById(principal.getId());
        if (userOp.isPresent()) {
            User userEntity = userOp.get();
            userEntity.setPassword(updateReqDto.getPassword());
            userEntity.setAddr(updateReqDto.getAddr());
        } else {
            throw new CustomApiException("해당 유저를 찾을 수 없습니다.");
        }
    }

    // 회원가입 시 유저아이디 중복체크하기
    public boolean 유저아이디중복체크(String username) {
        Optional<User> userOp = userRepository.findByUsername(username);

        if (userOp.isPresent()) {
            return false; // 이미 아이디 있으면 false
        } else {
            return true; // 아이디 없으면 true
        }
    }
}
