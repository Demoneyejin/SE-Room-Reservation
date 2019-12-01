package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationRepository  extends MongoRepository<Reservation, String> {

    List<Reservation> findByTimeAndDateAndRoomID(String time, String date, String roomID);
    List<Reservation> findByOwnerID(String ownerID);
}
