package hanghaehouse.hanghaehouse.domain.dto;

import hanghaehouse.hanghaehouse.domain.model.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String userName;

    @Column
    private String userProfile; // 이미지

    @ElementCollection
    private List<String> userInterested; // 빈 배열

    @Column
    private String token;

    @ElementCollection(fetch = FetchType.EAGER)
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
