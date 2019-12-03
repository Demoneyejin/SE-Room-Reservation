package com.reservo.reservo.DAL;

import com.reservo.reservo.Models.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface Reservation_DAL {

    List<Reservation> getReservationsByUser(String userID);
    Reservation makeNewReservation(Reservation newRes);
    List<Reservation> getReservationsByTime(LocalDate date, LocalTime time);
    List<Reservation> getReservationsByTimeAndRoom(LocalDate date, LocalTime time, List<String> roomID);


}
