package com.example.chatservice.domain;

import com.example.chatservice.entity.ChatMessage;
import com.example.chatservice.entity.ChatRoom;
import com.example.chatservice.domain.CommunityUsersDTO;
import com.example.chatservice.entity.Users;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;


public class ChatMessageDTO {
    public enum MessageType{
        ENTER, TALK, QUIT;
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class RequestDTO{
        private MessageType type;
        private Long roomId;
        private String content;
        private Long userId;

        public ChatMessage toEntity(Users users, ChatRoom chatRoom){
            return ChatMessage.builder()
                    .type(type)
                    .content(content)
                    .users(users)
                    .chatRoom(chatRoom)
                    .build();
        }

    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ResponseDTO{
        private MessageType type;
        private String roomId;
        private String content;
        private CommunityUsersDTO usersDTO;
        @JsonFormat(pattern = "HH:mm")
        private LocalDateTime createdAt;

        public static ResponseDTO fromEntity(ChatMessage chatMessage){
            return ResponseDTO.builder()
                    .type(chatMessage.getType())
                    .roomId(chatMessage.getChatRoom().getRoomId())
                    .content(chatMessage.getContent())
                    .usersDTO(chatMessage.getUsers().toIssueDto())
                    .createdAt(chatMessage.getCreatedAt())
                    .build();

        }

    }



}
