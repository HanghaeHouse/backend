package hanghaehouse.hanghaehouse.controller;

import hanghaehouse.hanghaehouse.domain.dto.ChatRoomDto;
import hanghaehouse.hanghaehouse.domain.model.ChatRoom;
import hanghaehouse.hanghaehouse.domain.model.LoginInfo;
import hanghaehouse.hanghaehouse.domain.repository.ChatRoomRepository;
import hanghaehouse.hanghaehouse.security.JwtTokenProvider;
import hanghaehouse.hanghaehouse.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    //채팅방 리스트 조회
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomService.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomId())));
        return chatRooms;
    }

    //관심사별 채팅방 리스트 조회
    //ChatRoomService에 접근해서 목록을 받아오면 HashOps 관련된 부분을 써야하기때문에 우선 Repository로 접근
    @GetMapping("/rooms/{interest}")
    @ResponseBody
    public List<ChatRoom> interestRoom(@PathVariable String interest) {
        List<ChatRoom> interestChatRooms = chatRoomRepository.findAllByUserInterested(interest);
        interestChatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomId())));
        return interestChatRooms;
    }

    //채팅방 생성(parameter : roomName, userInterested)
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestBody ChatRoomDto chatRoomDto) {
//        String roomName = chatRoom.get("roomName");
//        String userInterested = chatRoom.get("userInterested");
        ChatRoom createdRoom = chatRoomService.createChatRoom(chatRoomDto);
        chatRoomRepository.save(createdRoom);
        return createdRoom;
    }
//    @PostMapping("/room")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestBody Map<String, String> chatRoom) {
//        String roomName = chatRoom.get("roomName");
//        String userInterested = chatRoom.get("userInterested");
//        ChatRoom createdRoom = chatRoomService.createChatRoom(roomName, userInterested);
//        chatRoomRepository.save(createdRoom);
//        return createdRoom;
//    }

    //특정 채팅방 입장
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail"; //resources에 있는 roomdetail.ftl파일을 뷰 리졸버가 찾아서 연결해줌. 프론트 백 통신할떄는 빌드파일에 없어서 될지..
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomService.findRoomById(roomId);
    }

    // 로그인한 회원의 id 및 jwt 토큰 정보를 조회할 수 있도록 추가
    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().userName(name).token(jwtTokenProvider.createToken(name)).build();
    }
}
