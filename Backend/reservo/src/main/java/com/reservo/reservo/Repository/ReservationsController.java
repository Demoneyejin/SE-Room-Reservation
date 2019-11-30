package com.reservo.reservo.Repository;


import com.reservo.reservo.DAL.Reservation_DAL;
import com.reservo.reservo.DAL.Room_DAL;
import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Room;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reserve")
public class ReservationsController {

    private ReservationRepository reservationRepository;
    private Reservation_DAL reservationDAL;
    private Room_DAL roomDal;

    public ReservationsController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping("/{date}/{time}/{capacity}")
    public List<ReservationReturn> findOpenSlots(@PathVariable String date, @PathVariable String time, @PathVariable int capacity){

        System.out.println(capacity);

        List<String> roomIDs = roomDal.getAvailableRoomCapacityID(capacity).stream().map(Room::getRoomID).collect(Collectors.toList());

        List<Reservation> reservations = reservationDAL.getReservationsByTimeAndRoom(LocalDate.parse(date), LocalTime.parse(time), roomIDs);

        List<String> reservationIDs = reservations.stream().map(Reservation::getRoomID).collect(Collectors.toList());

        List<String> availableRoomIDs = roomIDs.stream().filter(id -> !reservationIDs.contains(id)).collect(Collectors.toList());

        ArrayList<ReservationReturn> returns = new ArrayList<>();

        availableRoomIDs.forEach(id -> returns.add(new ReservationReturn(date, roomDal.getRoomByID(id).getRoomName(),
                roomDal.getRoomByID(id).getCapacity(), time)));

        return returns;
    }


}

class ReservationReturn {

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
