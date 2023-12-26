package com.example.chatservice.repository;

import com.example.chatservice.entity.ChatRoom;
import com.example.chatservice.entity.ChatRoomUsers;
import com.example.chatservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUsersRepository extends JpaRepository<ChatRoomUsers, Long> {

    //챗룸에 유저 있는지 확인
    public Optional<ChatRoomUsers> findByChatRoomAndUsers(ChatRoom chatRoom, Users users);
    @Query("SELECT cu.chatRoom FROM ChatRoomUsers cu WHERE cu.users = :users")
    public List<ChatRoom> findChatRoomByUsers(@Param("users") Users users);
}
