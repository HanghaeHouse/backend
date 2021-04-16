package hanghaehouse.hanghaehouse.domain.dto;

import hanghaehouse.hanghaehouse.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDto {

    private Long id;
    private String email;
    private String userName;
    private String userProfile; // 이미지
    private List<String> userInterested; // 빈 배열
    private String token;
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public UserDto(String token, User user){
        this.id =user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.userProfile = user.getUserProfile();
        this.userInterested = user.getUserInterested();
        this.roles = user.getRoles();
        this.token = token;
    }

}
