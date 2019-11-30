package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByCapacityGreaterThanEqual(int capacity);

}
