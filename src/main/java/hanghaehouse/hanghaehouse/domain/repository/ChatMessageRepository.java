package hanghaehouse.hanghaehouse.domain.repository;

import hanghaehouse.hanghaehouse.domain.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomIdOrderByTimenowDesc(String roomId);
}
