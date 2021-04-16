package hanghaehouse.hanghaehouse.controller;

import hanghaehouse.hanghaehouse.domain.model.ChatMessage;
import hanghaehouse.hanghaehouse.security.JwtTokenProvider;
import hanghaehouse.hanghaehouse.service.ChatRoomService;
import hanghaehouse.hanghaehouse.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {//ChatService에서 입/퇴장을 처리하기 때문에 간소

    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/api/chat/message") // 웹소켓으로 들어오는 메시지 발행 처리 -> 클라이언트에서는 /pub/chat/message로 발행 요청
    public void message(@RequestBody ChatMessage message, @Header("token") String token) {
        String nickname = jwtTokenProvider.getUserPk(token); //회원의 대화명을 가져와 token 유효성 체크
        // 헤더에서 토큰을 읽어 로그인 회원 정보로 대화명 설정
        message.setSender(nickname);
        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomService.getUserCount(message.getRoomId()));
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message); // 메서드 일원화
    }
}