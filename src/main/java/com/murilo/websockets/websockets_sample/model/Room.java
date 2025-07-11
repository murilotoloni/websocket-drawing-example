package com.murilo.websockets.websockets_sample.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private String id;

    private List<String> players;

    private List<Object> data = new ArrayList<>();
}
