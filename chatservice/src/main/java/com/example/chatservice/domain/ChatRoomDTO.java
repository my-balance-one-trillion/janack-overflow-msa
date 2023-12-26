package com.example.chatservice.domain;

import com.example.chatservice.entity.ChatRoom;
import com.example.chatservice.entity.ChatRoomUsers;
import com.example.chatservice.domain.CommunityUsersDTO;
import com.example.chatservice.entity.Users;
import lombok.*;

import java.util.List;


public class ChatRoomDTO {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class RequestDTO{
        private String roomName;
        private String category;
        public ChatRoom toEntity(Users users, String roomId, List<ChatRoomUsers> chatRoomUsers){
            return ChatRoom.builder()
                    .roomName(roomName)
                    .category(category)
                    .roomId(roomId)
                    .users(users)
                    .usersList(chatRoomUsers)
                    .max(4)
                    .build();
        }
    }
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ResponseDTO {
        private Long id;
        private String roomId;
        private String roomName;
        private String category;
        private int max;
        private Long usersId;
        private List<CommunityUsersDTO> usersList;

        public static ResponseDTO fromEntity(ChatRoom chatRoom) {
            return ResponseDTO.builder()
                    .id(chatRoom.getId())
                    .roomId(chatRoom.getRoomId())
                    .roomName(chatRoom.getRoomName())
                    .category(chatRoom.getCategory())
                    .max(chatRoom.getMax())
                    .usersId(chatRoom.getUsers().getId())
                    .usersList(chatRoom.getUsersList().
                            stream().map(ChatRoomUsers::toCommunityUsersDTO)
                            .toList())
                    .build();
        }
    }
}
