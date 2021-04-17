package hanghaehouse.hanghaehouse.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable { // redis에 저장되는 객체들은 Serialize가 가능해야 함, -> Serializable 참조

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String roomName;
    private String userInterested;
    private long userCount; // 채팅방 인원수

    public static ChatRoom create(String name, String userInterested) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = name;
        chatRoom.userInterested = userInterested;
        return chatRoom;
    }
}
