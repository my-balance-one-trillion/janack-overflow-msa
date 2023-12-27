package com.example.janackoverflow.openfeign;

import com.example.janackoverflow.chat.domain.ChatRoomDTO;
import com.example.janackoverflow.global.security.auth.NowUserDetails;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/main-service/chatrooms")
@Slf4j
public class ChatController {
    @Autowired
    private ChatService chatService;



    @GetMapping
    public ResponseEntity<?> getAllChatRooms(){
        return chatService.getAllChatRooms();
    }
    @GetMapping("/my")
    public ResponseEntity<?> getJoinedRooms(@AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getUser().getId();
        return chatService.getJoinedRooms(usersId);
    }
    @GetMapping("/createdRoom")
    public ResponseEntity<?> getCreatedRooms(@AuthenticationPrincipal NowUserDetails nowUserDetails){
        Long usersId = nowUserDetails.getUser().getId();
        ResponseEntity<?> response = chatService.getCreatedRooms(usersId);

        return response;
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<Map<String, Object>> chatList(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                                        @PathVariable(value = "roomId") Long roomId){
        log.info("방 들어가기 실행");
        Long usersId = nowUserDetails.getUser().getId();
        ResponseEntity<Map<String, Object>> response = chatService.chatList(usersId, roomId);
        System.out.println("방 정보:"+ response.getBody());
        return response;
    }

    @GetMapping("/enter/{roomId}")
    public ResponseEntity<String> enterChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                               @PathVariable(value = "roomId") Long roomId){
        log.info("입장 실행");
        Long usersId = nowUserDetails.getUser().getId();

        ResponseEntity<String> response = chatService.enterChatRoom(usersId, roomId);
        System.out.println("입장 body:"+response.getBody());
        return response;
    }
    @DeleteMapping("/quit/{roomId}")
    public ResponseEntity<String> quitChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                          @PathVariable(value = "roomId") Long roomId){
        log.info("퇴장 실행");
        Long usersId = nowUserDetails.getUser().getId();
        ResponseEntity<String> response = chatService.quitChatRoom(usersId, roomId);
        System.out.println("퇴장 body:"+response.getBody());
        return response;
    }
    @PostMapping
    public ResponseEntity<?> createChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                            @RequestBody ChatRoomDTO.RequestDTO chatRoomDTO){
        Long usersId = nowUserDetails.getUser().getId();
        ResponseEntity<?> response = chatService.createChatRoom(usersId, chatRoomDTO);
        System.out.println("방 생성:"+response.getBody());
        return response;
    }
    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteChatRoom(@AuthenticationPrincipal NowUserDetails nowUserDetails,
                                            @PathVariable(value = "roomId") Long roomId){
        Long usersId = nowUserDetails.getUser().getId();
        return chatService.deleteChatRoom(usersId, roomId);
    }

}
