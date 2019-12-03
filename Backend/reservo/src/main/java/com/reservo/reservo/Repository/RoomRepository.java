package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByCapacityGreaterThanEqual(int capacity);
    Room findByRoomName(String roomName);

}
