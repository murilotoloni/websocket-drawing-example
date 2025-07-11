package com.murilo.websockets.websockets_sample.controller;

import com.murilo.websockets.websockets_sample.RoomService;
import com.murilo.websockets.websockets_sample.model.JoinRequest;
import com.murilo.websockets.websockets_sample.model.Room;
import com.murilo.websockets.websockets_sample.model.RoomMessage;
import com.murilo.websockets.websockets_sample.model.WebSocketMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class RoomController {

    private final SimpMessagingTemplate messagingTemplate;

    private final RoomService roomService;

    public RoomController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }

    @MessageMapping("/join")
    public void handleJoin(JoinRequest joinRequest) {
        Room room = this.roomService.getRoom(joinRequest.getRoomId()).orElseThrow(() -> new RuntimeException("Room Not Found"));
        if(room.getPlayers().contains(joinRequest.getPlayerId())) {
            return;
        }
        room.getPlayers().add(joinRequest.getPlayerId());
        this.roomService.updateRoom(room);




        // Broadcast para todos na sala
        messagingTemplate.convertAndSend(
                "/topic/room/" + joinRequest.getRoomId(),
                new WebSocketMessage("PLAYER_JOINED", joinRequest.getPlayerId())
        );

        room.getData().forEach(object -> {

            RoomMessage.MessageData messageData = new RoomMessage.MessageData();
            messageData.setPlayerId(joinRequest.getPlayerId());
            messageData.setCanvas(object);
            RoomMessage roomMessage = new RoomMessage();
            roomMessage.setType("CANVAS_REFRESH");
            roomMessage.setData(messageData);

            messagingTemplate.convertAndSend(
                    "/topic/room/" + joinRequest.getRoomId(),
                    roomMessage
            );
        });

    }

    @MessageMapping("/room/{roomId}")
    public void handleRoomMessage(@DestinationVariable String roomId, @Payload RoomMessage message) {

        if("CANVAS_UPDATE".equals(message.getType())) {
            Room room = this.roomService.getRoom(roomId).orElseThrow(() -> new RuntimeException("room not found"));
            room.getData().add(message.getData().getCanvas());
            this.roomService.updateRoom(room);

            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId,
                    message
            );
        }

        if("CANVAS_CLEAR".equals(message.getType())) {
            Room room = this.roomService.getRoom(roomId).orElseThrow(() -> new RuntimeException("room not found"));
            room.setData(new ArrayList<>());
            this.roomService.updateRoom(room);

            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId,
                    message
            );
        }


    }
}
