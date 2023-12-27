package com.example.janackoverflow.openfeign;

import com.example.janackoverflow.chat.domain.ChatRoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "chat-service", url = "http://localhost:8082", configuration = OpenFeignConfig.class)
public interface ChatService {
    @GetMapping("/chatrooms")
    ResponseEntity<List<ChatRoomDTO.ResponseDTO>> getAllChatRooms();

    @GetMapping("/chatrooms/my")
    ResponseEntity<List<ChatRoomDTO.ResponseDTO>> getJoinedRooms(@RequestParam("usersId") Long usersId);

    @GetMapping("/chatrooms/createdRoom")
    ResponseEntity<List<ChatRoomDTO.ResponseDTO>> getCreatedRooms(@RequestParam("usersId") Long usersId);

    @GetMapping("/chatrooms/{roomId}")
    ResponseEntity<Map<String, Object>> chatList(@RequestParam("usersId") Long usersId,
                                                 @PathVariable(value = "roomId") Long roomId);

    @GetMapping("/chatrooms/enter/{roomId}")
    ResponseEntity<String> enterChatRoom(@RequestParam("usersId") Long usersId,
                                    @PathVariable(value = "roomId") Long roomId);

    @DeleteMapping("/chatrooms/quit/{roomId}")
    ResponseEntity<String> quitChatRoom(@RequestParam("usersId") Long usersId,
                                   @PathVariable(value = "roomId") Long roomId);

    @PostMapping("/chatrooms")
    ResponseEntity<?> createChatRoom(@RequestParam("usersId") Long usersId,
                                     @RequestBody ChatRoomDTO.RequestDTO chatRoomDTO);

    @DeleteMapping("/chatrooms/{roomId}")
    ResponseEntity<String> deleteChatRoom(@RequestParam("usersId") Long usersId,
                                          @PathVariable(value = "roomId") Long roomId);


}