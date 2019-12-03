package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository  extends MongoRepository<Reservation, String> {

    List<Reservation> findByTimeAndDateAndRoomIDIn(LocalTime time, LocalDate date, List<String> roomID);
    List<Reservation> findByOwnerID(String ownerID);
    boolean existsByTimeAndDateAndRoomID(LocalTime time, LocalDate date, String roomID);
}
