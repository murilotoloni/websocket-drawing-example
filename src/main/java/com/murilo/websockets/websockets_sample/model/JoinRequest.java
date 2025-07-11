package com.murilo.websockets.websockets_sample.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String roomId;
    private String playerId;

}