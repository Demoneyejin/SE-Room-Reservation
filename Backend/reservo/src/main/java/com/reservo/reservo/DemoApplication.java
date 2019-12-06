package com.reservo.reservo;

import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Room;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.ReservationRepository;
import com.reservo.reservo.Repository.RoomRepository;
import com.reservo.reservo.Repository.UserRepository;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

@ComponentScan
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoomRepository roomRepository;

	private static AnnotationConfigApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception{


	}


}
