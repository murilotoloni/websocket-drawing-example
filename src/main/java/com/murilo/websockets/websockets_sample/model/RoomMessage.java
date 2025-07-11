package com.murilo.websockets.websockets_sample.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomMessage {
    private String type;
    private MessageData data;


    @Getter
    @Setter
    public static class MessageData {
        private String playerId;
        private Object canvas;
    }
}

