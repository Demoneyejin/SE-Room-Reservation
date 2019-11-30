package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {

}
