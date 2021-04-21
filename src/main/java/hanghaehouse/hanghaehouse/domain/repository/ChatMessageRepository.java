package hanghaehouse.hanghaehouse.domain.repository;

import hanghaehouse.hanghaehouse.domain.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
