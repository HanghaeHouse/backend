package hanghaehouse.hanghaehouse.service;

import hanghaehouse.hanghaehouse.domain.dto.UserDto;

import hanghaehouse.hanghaehouse.domain.model.User;
import hanghaehouse.hanghaehouse.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void update(UserDto userDto){
        String email = userDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        user.update(userDto);
    }
}