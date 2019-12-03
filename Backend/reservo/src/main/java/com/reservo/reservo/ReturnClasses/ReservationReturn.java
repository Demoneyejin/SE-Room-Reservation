package com.reservo.reservo.ReturnClasses;

import com.reservo.reservo.Models.Reservation;
import org.springframework.lang.Nullable;
import java.util.List;

public class ReservationReturn {

    private String date;
    private String room;
    private int capacity;
    private String time;

    public ReservationReturn(String date, String room, int capacity, String time){
        this.date = date;
        this.room = room;
        this.capacity = capacity;
        this.time = time;
    }


    public String getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getTime() {
        return time;
    }
}
