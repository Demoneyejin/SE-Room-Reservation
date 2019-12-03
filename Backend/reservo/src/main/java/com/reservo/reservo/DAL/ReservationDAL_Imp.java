package com.reservo.reservo.DAL;

import com.reservo.reservo.Models.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDAL_Imp implements Reservation_DAL {

    @Autowired
    private MongoTemplate template;


    @Override
    public List<Reservation> getReservationsByUser(String userID) {

        Query query = new Query();
        query.addCriteria(Criteria.where("userID").is(userID));
        return template.find(query, Reservation.class);
    }

    @Override
    public Reservation makeNewReservation(Reservation newRes) {
        template.save(newRes);
        return newRes;
    }

    @Override
    public List<Reservation> getReservationsByTime(LocalDate date, LocalTime time) {
        Query query = new Query();
        query.addCriteria(Criteria.where("date").is(date).and("time").is(time));
        return template.find(query, Reservation.class);
    }

    @Override
    public List<Reservation> getReservationsByTimeAndRoom(LocalDate date, LocalTime time, List<String> roomID) {

        Query query = new Query();
        query.addCriteria(Criteria.where("date").is(date).and("time").is(time).and("roomID").in(roomID));
        return null;
    }
}
