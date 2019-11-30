package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository  extends MongoRepository<Reservation, String> {
    
}
