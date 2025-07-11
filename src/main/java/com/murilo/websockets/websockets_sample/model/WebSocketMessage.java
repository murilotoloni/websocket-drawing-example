package com.murilo.websockets.websockets_sample.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {

    private String type;
    private Object payload;

}