package com.reservo.reservo.DAL;

import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Room;

import java.util.List;

public interface Room_DAL {

    List<Room> getAllAvailableRoomsID();

    // Gets all the roomIDs of rooms with a capacity above a certain threshold
    List<Room> getAvailableRoomCapacityID(int capacity);

    Room getRoomByID(String roomID);



}
