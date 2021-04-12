package hanghaehouse.hanghaehouse.controller;


import hanghaehouse.hanghaehouse.domain.dto.UserDto;
import hanghaehouse.hanghaehouse.domain.model.User;
import hanghaehouse.hanghaehouse.domain.repository.UserRepository;
import hanghaehouse.hanghaehouse.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/api/signup")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .userName(user.get("userName"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

//    // 로그인 (롤 추가 형태)
//    @PostMapping("/api/login")
//    public String login(@RequestBody Map<String, String> user) {
//        User member = userRepository.findByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        return jwtTokenProvider.createToken(member.getEmail(), member.getRoles());
//    }

    @PostMapping("/api/login")
    public UserDto login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken(member.getEmail());
        UserDto User = new UserDto(token,member);
        return User;
    }


    //Request의 Header로 넘어온 token을 쪼개어 유저정보 확인해주는 과정 _ return value: Optional<User>
    @RequestMapping("/api/logincheck")
    public Optional<User> userInfo(HttpServletRequest httpServletRequest) {
    /*
    HTTP Request의 Header로 넘어온 token을 쪼개어 누구인지 나타내주는 과정
     */
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String email = jwtTokenProvider.getUserPk(token);
        return userRepository.findByEmail(email);
    }

}