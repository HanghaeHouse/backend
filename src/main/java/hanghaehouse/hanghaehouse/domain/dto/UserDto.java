package hanghaehouse.hanghaehouse.domain.dto;
import hanghaehouse.hanghaehouse.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONArray;
//import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.json.JSONObject;
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
    //관심사 교체
    public UserDto(User user, List<String> userInterested){
        this.id =user.getId();
        this.email = user.getEmail();
        this.userName = user.getUsername();
        this.userProfile = user.getUserProfile();
        this.userInterested = userInterested;
        this.roles = user.getRoles();

    }

    //프로필 교체
    public UserDto(User user, JSONObject userJson) {
        this.id =user.getId();
        this.email = user.getEmail();
        this.userName = userJson.getString("userName");
        this.userProfile = userJson.getString("userProfile");
        if(userJson.isNull("userInterested")){
            this.userInterested = new ArrayList<>();
        } else {
            this.userInterested = new ArrayList<>();
            JSONArray interest_array = userJson.getJSONArray("userInterested");
            for(int i = 0; i < interest_array.length() ; i++){
                this.userInterested.add(interest_array.getString(i));
            }
        }
        this.roles = user.getRoles();

    }
}