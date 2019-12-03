package com.reservo.reservo;

import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Room;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.ReservationRepository;
import com.reservo.reservo.Repository.RoomRepository;
import com.reservo.reservo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoomRepository roomRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{


	}


}
