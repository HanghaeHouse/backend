package hanghaehouse.hanghaehouse.Controller;

import hanghaehouse.hanghaehouse.domain.User;
import hanghaehouse.hanghaehouse.domain.UserRepository;
import hanghaehouse.hanghaehouse.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/api/signup")
    public Long signup(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .username(user.get("username"))
                .password(passwordEncoder.encode(user.get("password")))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByUsername(user.get("username"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 회원 이름 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername());
    }
}