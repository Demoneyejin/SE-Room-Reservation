package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Roles;
import com.reservo.reservo.Models.Room;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.ReturnClasses.CurrentReservationsReturn;
import com.reservo.reservo.ReturnClasses.ReservationReturn;
import com.reservo.reservo.ReturnClasses.RoleReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/reserve")
public class ReservationsController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ReservationRepository reservationRepository;

    @Autowired(required = true)
    private RoomRepository roomRepository;

    @Autowired(required = true)
    private UserRepository userRepository;

    @Autowired(required = true)
    private RolesRepository rolesRepository;


    @RequestMapping(value="/{date}/{time}/{capacity}", method= RequestMethod.GET)
    public List<ReservationReturn> findOpenSlots(@PathVariable String date, @PathVariable String time, @PathVariable int capacity){

        // All rooms with enough capacity
        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqual(capacity);

        List<String> roomIDs = rooms.stream().map(Room::getRoomID).collect(Collectors.toList());

        List<Reservation> reservations = reservationRepository.findByTimeAndDateAndRoomIDIn(LocalTime.parse(time), LocalDate.parse(date), roomIDs);

        reservations.forEach(System.out::println);

        // Checks to make sure there are actually reservations to search through, If not just send all the rooms that apply
        if (reservations != null && !reservations.isEmpty()) {
            //List containing the ID's of all the rooms with a reservation at the time and date
            List<String> roomIDsWReservations = reservations.stream().map(Reservation::getRoomID).collect(Collectors.toList());
            roomIDsWReservations.forEach(System.out::println);
            rooms = rooms.stream().filter(room -> !roomIDsWReservations.contains(room.getRoomID())).collect(Collectors.toList());
        }

        ArrayList<ReservationReturn> returns = new ArrayList<>();

        rooms.forEach(room -> returns.add(new ReservationReturn(date, room.getRoomName(),
                                                                room.getCapacity(), time)));

        return returns;
    }

    @RequestMapping(value = "/make", method=RequestMethod.POST)
    public Reservation makeNewReservation(@RequestBody Map<String, String> info){
        // Checks to make sure there are no other reservations for the combination of room, date, and time
        if (!reservationRepository.existsByTimeAndDateAndRoomID(LocalTime.parse(info.get("time")), LocalDate.parse(info.get("date")),
                                                                roomRepository.findByRoomName(info.get("room")).getRoomID())) {

            String userId = userRepository.findByUserName(info.get("username")).getUserID();
            String roomID = roomRepository.findByRoomName(info.get("room")).getRoomID();
            LocalDate date = LocalDate.parse(info.get("date"));
            LocalTime time = LocalTime.parse(info.get("time"));

            return reservationRepository.save(new Reservation(userId, roomID, time, date));

        }

        else
            return null;
    }

    @RequestMapping(value = "/rooms/all", method = RequestMethod.GET)
    public List<Room> getRooms(){

        return roomRepository.findAll();

    }

    @RequestMapping(value = "/{userEmail}", method=RequestMethod.GET)
    public List<CurrentReservationsReturn> getReservationFromUser(@PathVariable String userEmail){

        String ownerID = userRepository.findByUserName(userEmail).getUserID();

        List<Reservation> reservations = reservationRepository.findByOwnerID(ownerID);
        reservations.forEach(res -> {
            Optional<Room> room = roomRepository.findById(res.getRoomID());
            room.ifPresent(value -> res.setRoomID(value.getRoomName()));
            res.setOwnerID(userEmail);
        });

        List<CurrentReservationsReturn> returns = new ArrayList<>();

        reservations.forEach(res -> {
            List<RoleReturn> roles = rolesRepository.findByReservationID(res.getReservationID())
                    .stream().map(role -> {
                                      final String[] userName = new String[1];
                        userRepository.findById(role.getUserID()).ifPresent(user -> userName[0] = user.getUserEmail());
                        return new RoleReturn(role, userName[0]);
                    }
                        ).collect(Collectors.toList());

            returns.add(new CurrentReservationsReturn(res, roles));

        });

        return returns;
    }

    @RequestMapping(value="/role/add", method = RequestMethod.POST)
    public Roles addUserRoles(@RequestBody Map<String, String> payload){

        if (payload.isEmpty()) {
            throw new NullPointerException("No payload found");
        }

        if (!userRepository.existsByUserEmail(payload.get("userID"))) {
            throw new NullPointerException("No user found");
        }

        if (!reservationRepository.existsById(payload.get("reservationID"))) {
            throw new NullPointerException("Reservation not found");
        }

        if (payload.get("roles") == null){
            throw new NullPointerException("No role included");
        }

        User user = userRepository.findByUserEmail(payload.get("userID"));

        Optional<Roles> rolesOptional= rolesRepository.findByReservationIDAndUserID(payload.get("reservationID"), user.getUserID());

        Roles tempRole;

        tempRole = rolesOptional.orElseGet(() -> new Roles(payload.get("reservationID"), user.getUserID(), new ArrayList<>()));

        tempRole.addRole(payload.get("roles"));
        return rolesRepository.save(tempRole);

    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public String removeReservation(@PathVariable String id){

        if (!reservationRepository.existsById(id)){
            throw new NullPointerException("No reservation by that ID");
        }

        rolesRepository.deleteAllByReservationID(id);

        long before = reservationRepository.count();

        reservationRepository.deleteById(id);

        if (before == reservationRepository.count() + 1){

            LOG.info("Removed Reservation ID: {}", id);

            return "Successfully removed";
        }
        else {
            throw new IllegalStateException("Could not remove");
        }

    }

}
