package site.metacoding.springlogin.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.springlogin.domain.User;
import site.metacoding.springlogin.domain.UserRepository;

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
