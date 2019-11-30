package com.reservo.reservo.Repository;


import com.reservo.reservo.DAL.ReservationDAL_Imp;
import com.reservo.reservo.DAL.Reservation_DAL;
import com.reservo.reservo.DAL.Room_DAL;
import com.reservo.reservo.DAL.Room_DAL_IMP;
import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reserve")
public class ReservationsController {
    
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    private Reservation_DAL reservationDAL;
    private Room_DAL roomDal;

    public ReservationsController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
        this.roomDal = new Room_DAL_IMP();
        this.reservationDAL = new ReservationDAL_Imp();
    }

    @RequestMapping("/{date}/{time}/{capacity}")
    public List<ReservationReturn> findOpenSlots(@PathVariable String date, @PathVariable String time, @PathVariable int capacity){

        System.out.println(capacity);

        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqual(capacity);

        List<String> roomIDs = rooms.stream().map(Room::getRoomID).collect(Collectors.toList());

        List<Reservation> reservations = reservationDAL.getReservationsByTimeAndRoom(LocalDate.parse(date), LocalTime.parse(time), roomIDs);

        if (reservations != null) {
            List<String> reservationIDs = reservations.stream().map(Reservation::getRoomID).collect(Collectors.toList());
            rooms = rooms.stream().filter(room -> !reservationIDs.contains(room.getRoomID())).collect(Collectors.toList());
        }

        ArrayList<ReservationReturn> returns = new ArrayList<>();

        rooms.forEach(room -> returns.add(new ReservationReturn(date, room.getRoomName(),
                room.getCapacity(), time)));

        return returns;
    }

    @RequestMapping("/rooms/all")
    public List<Room> getRooms(){

        return roomRepository.findAll();

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
