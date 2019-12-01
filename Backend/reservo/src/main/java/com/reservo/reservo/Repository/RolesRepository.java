package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RolesRepository extends MongoRepository<Roles, String> {

    List<Roles> findByReservationID(String reservationID);

}
