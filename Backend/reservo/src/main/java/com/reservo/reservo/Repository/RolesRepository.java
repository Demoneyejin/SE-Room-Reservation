package com.reservo.reservo.Repository;

import com.reservo.reservo.Models.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {

    List<Roles> findByReservationID(String reservationID);

    Optional<Roles> findByReservationIDAndUserID(String reservationID, String userID);

    void deleteAllByReservationID(String reservationID);

}
