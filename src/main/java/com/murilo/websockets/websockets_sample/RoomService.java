package com.murilo.websockets.websockets_sample;

import com.murilo.websockets.websockets_sample.model.Room;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    RoomService() {
        rooms.put("123", new Room("123", new ArrayList<>(), new ArrayList<>()));
    }

    public Optional<Room> getRoom(String roomId) {
        Room room = this.rooms.get(roomId);
        return Optional.ofNullable(room);
    }

    public Room createRoom(String playerId) {
        String id = UUID.randomUUID().toString();
        Room room = new Room(id, List.of(playerId), null);

        rooms.put(id, room);

        return room;
    }

    public void updateRoom(Room room) {
        this.rooms.put(room.getId(), room);
    }
}
