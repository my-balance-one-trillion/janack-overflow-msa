package com.example.chatservice.controller;


import com.example.chatservice.domain.ChatMessageDTO;
import com.example.chatservice.domain.ChatRoomDTO;
import com.example.chatservice.entity.ChatRoom;
import com.example.chatservice.entity.Users;
import com.example.chatservice.service.ChatMessageService;
import com.example.chatservice.service.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatrooms")
@Slf4j
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatMessageService chatMessageService;

    //모든 채팅방 리스트
    @GetMapping
    public ResponseEntity<List<ChatRoomDTO.ResponseDTO>> getAllChatRooms() {
        try {
            List<ChatRoomDTO.ResponseDTO> chatRoomDTOList = chatRoomService.readAll();
            return ResponseEntity.ok(chatRoomDTOList);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().build();
        }
    }

    //들어가있는 채팅방
    @GetMapping("/my")
    public ResponseEntity<List<ChatRoomDTO.ResponseDTO>> getJoinedRooms(@RequestParam("usersId") Long usersId) {
        try {
            List<ChatRoomDTO.ResponseDTO> chatRoomDTOList = chatRoomService.readJoinedRooms(usersId);
            return ResponseEntity.ok(chatRoomDTOList);
        } catch (Exception e) {
            String error = e.getMessage();
            return ResponseEntity.badRequest().build();
        }
    }

    //내가 만든 방
    @GetMapping("/createdRoom")
    public ResponseEntity<List<ChatRoomDTO.ResponseDTO>> getCreatedRooms(@RequestParam("usersId") Long usersId) {
        List<ChatRoomDTO.ResponseDTO> chatRoomDTOList = chatRoomService.readCreatedRoom(usersId);

        return ResponseEntity.ok(chatRoomDTOList);
    }

    //채팅 내역 불러오기+입장
    @GetMapping("/{roomId}")
    public ResponseEntity<Map<String, Object>> chatList(@RequestParam("usersId") Long usersId,
                                      @PathVariable(value = "roomId") Long roomId) {
        try {
            ChatRoomDTO.ResponseDTO chatRoomDTO = chatRoomService.read(roomId);
            List<ChatMessageDTO.ResponseDTO> chatMessageList = chatMessageService.readChatMessages(roomId, usersId);
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("roomInfo", chatRoomDTO);
            responseMap.put("messageList", chatMessageList);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            String error = e.getMessage();
            log.error(error);
            return ResponseEntity.badRequest().build();
        }
    }

    //유저 입장
    @GetMapping("/enter/{roomId}")
    public ResponseEntity<String> enterChatRoom(@RequestParam("usersId") Long usersId,
                                           @PathVariable(value = "roomId") Long roomId) {

        //유저 수 다 찼을때 못들어오게 하기
            try {
                chatRoomService.enterChatRoom(roomId, usersId);
                return ResponseEntity.ok("입장");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }

    }


    //유저 퇴장
    @DeleteMapping("/quit/{roomId}")
    public ResponseEntity<?> quitChatRoom(@RequestParam("usersId") Long usersId,
                                          @PathVariable(value = "roomId") Long roomId){
        try {
            chatRoomService.quitChatRoom(roomId, usersId);
            return ResponseEntity.ok("퇴장");
        } catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.badRequest().body("퇴장에 실패하였습니다.");
        }
    }

    //채팅방 생성
    @PostMapping
    public ResponseEntity<?> createChatRoom(@RequestParam("usersId") Long usersId,
                                            @RequestBody ChatRoomDTO.RequestDTO chatRoomDTO){

        try {
            ChatRoom chatRoom = chatRoomService.create(chatRoomDTO, usersId);
            log.info("생성된 방 id {}", chatRoom.getId());
            return ResponseEntity.created(URI.create("/chat/" + chatRoom.getId())).body(ChatRoomDTO.ResponseDTO.fromEntity(chatRoom));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("존재하지 않는 방입니다.");
        }
    }
    //채팅방 삭제
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteChatRoom(@RequestParam("usersId") Long usersId,
                                                 @PathVariable(value = "roomId") Long roomId){
        log.info("실행됨");

            if(chatRoomService.delete(roomId, usersId)){
                return ResponseEntity.ok("삭제되었습니다.");
            }
            else {
                return ResponseEntity.badRequest().body("채팅방 생성자가 아닙니다.");
            }

        }





}
