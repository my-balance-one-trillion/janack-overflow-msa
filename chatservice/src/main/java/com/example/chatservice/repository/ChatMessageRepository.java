package com.example.chatservice.repository;

import com.example.chatservice.domain.ChatMessageDTO;
import com.example.chatservice.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    //채팅 내역 불러오기
    public List<ChatMessage> findByChatRoomIdOrderByCreatedAt(Long roomId);

    //가장 최근 입장or퇴장 메시지 불러오기
    public Optional<ChatMessage> findTop1ByChatRoomIdAndUsersIdAndTypeOrderByCreatedAtDesc(Long roomId, Long usersId, ChatMessageDTO.MessageType type);
    
    public List<ChatMessage> findByChatRoomIdAndIdGreaterThanEqual(Long roomId, Long chatMessageId);
}
