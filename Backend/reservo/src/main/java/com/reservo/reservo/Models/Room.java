package com.reservo.reservo.Models;

import org.springframework.data.annotation.Id;

public class Room {

    @Id
    private String roomID;
    private String roomName;
    private int capacity;
    private boolean isAvailable;

    public Room(String roomName, int capacity, boolean isAvailable){
        this.roomName = roomName;
        this.capacity = capacity;
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomID() {
        return roomID;
    }
}
