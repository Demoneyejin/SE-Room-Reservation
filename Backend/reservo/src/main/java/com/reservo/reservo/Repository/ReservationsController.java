package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Roles;
import com.reservo.reservo.Models.Room;
import com.reservo.reservo.ReturnClasses.CurrentReservationsReturn;
import com.reservo.reservo.ReturnClasses.ReservationReturn;
import com.reservo.reservo.ReturnClasses.RoleReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reserve")
public class ReservationsController {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;

    public ReservationsController(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    @RequestMapping(value="/{date}/{time}/{capacity}", method= RequestMethod.GET)
    public List<ReservationReturn> findOpenSlots(@PathVariable String date, @PathVariable String time, @PathVariable int capacity){

        System.out.println(capacity);

        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqual(capacity);

        System.out.println(rooms.toString());

        List<String> roomIDs = rooms.stream().map(Room::getRoomID).collect(Collectors.toList());

        List<Reservation> reservations = reservationRepository.findByTimeAndDateAndRoomIDIn(LocalTime.parse(time), LocalDate.parse(date), roomIDs);

        // Checks to make sure there are actually reservations to search through, If not just send all the rooms
        if (reservations != null) {
            //List containing the ID's of all the rooms with a reservation at the time and date
            List<String> reservationIDs = reservations.stream().map(Reservation::getRoomID).collect(Collectors.toList());
            rooms = rooms.stream().filter(room -> !reservationIDs.contains(room.getRoomID())).collect(Collectors.toList());
        }

        ArrayList<ReservationReturn> returns = new ArrayList<>();

        rooms.forEach(room -> returns.add(new ReservationReturn(date, room.getRoomName(),
                                                                room.getCapacity(), time)));

        System.out.println(returns);

        return returns;
    }

    @RequestMapping(value = "/make", method=RequestMethod.POST)
    public Reservation makeNewReservation(@RequestBody ReservationReturn reservation, String userName){
        // Checks to make sure there are no other reservations for the combination of room, date, and time
        if (!reservationRepository.existsByTimeAndDateAndRoomID(LocalTime.parse(reservation.getTime()), LocalDate.parse(reservation.getDate()),
                                                                roomRepository.findByRoomName(reservation.getRoom()).getRoomID())) {

            return reservationRepository.save(new Reservation(userRepository.findByUserName(userName).getUserID(),
                                                              roomRepository.findByRoomName(reservation.getRoom()).getRoomID(),
                                                              new HashMap<String, String>(), LocalTime.parse(reservation.getTime()),
                                                              LocalDate.parse(reservation.getDate())));}
        else
            return null;
    }

    @RequestMapping("/rooms/all")
    public List<Room> getRooms(){

        return roomRepository.findAll();

    }

    @RequestMapping(value = "/{userEmail}", method=RequestMethod.GET)
    public List<CurrentReservationsReturn> getReservationFromUser(@PathVariable String userEmail){

        List<Reservation> reservations = reservationRepository.findByOwnerID(userEmail);
        reservations.forEach(res -> {
            Optional<Room> room = roomRepository.findById(res.getRoomID());
            room.ifPresent(value -> res.setRoomID(value.getRoomName()));
        });

        List<CurrentReservationsReturn> returns = new ArrayList<>();

        reservations.forEach(res -> {
            List<RoleReturn> roles = rolesRepository.findByReservationID(res.getReservationID())
                    .stream().map(RoleReturn::new).collect(Collectors.toList());

            returns.add(new CurrentReservationsReturn(res, roles));

        });

        return returns;
    }

    @RequestMapping(value="/role/add", method = RequestMethod.POST)
    public Roles addUserRoles(@RequestBody Map<String, String> payload){

        if (payload.isEmpty()) {
            throw new NullPointerException("No payload found");
        }

        if (!userRepository.existsById(payload.get("userID"))) {
            throw new NullPointerException("No user found");
        }

        if (!reservationRepository.existsById(payload.get("reservationRepository"))) {
            throw new NullPointerException("Reservation not found");
        }

        Optional<Roles> rolesOptional= rolesRepository.findByReservationIDAndUserID(payload.get("reservationID"), payload.get("userID"));

        if (rolesOptional.isPresent()){
            Roles tempRole = rolesOptional.get();
            tempRole.addRole(payload.get("role"));
            return rolesRepository.save(tempRole);
        }
        else {

            Roles tempRole = new Roles(payload.get("reservationID"), payload.get("userID"), new ArrayList<>());
            tempRole.addRole(payload.get("role"));
            return rolesRepository.save(tempRole);
        }
        
    }

}
