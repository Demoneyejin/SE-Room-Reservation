package com.reservo.reservo.DAL;

import com.reservo.reservo.Models.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class Room_DAL_IMP implements Room_DAL{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Room> getAllAvailableRoomsID() {

        Query query = new Query();
        query.addCriteria(Criteria.where("isAvailable").is(true));

        //Gets all the roomIDs of the query
        return mongoTemplate.find(query, Room.class);
    }

    @Override
    public List<Room> getAvailableRoomCapacityID(int capacity) {

        Query query = new Query();
        query.addCriteria(Criteria.where("isAvailable").is(true).and("capacity").gte(capacity));

        System.out.println(mongoTemplate.toString());

        // Gets all the roomIDs of the query
        return mongoTemplate.find(query, Room.class);
    }

    @Override
    public Room getRoomByID(String roomID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomID").is(roomID));
        return mongoTemplate.findOne(query, Room.class);
    }
}
