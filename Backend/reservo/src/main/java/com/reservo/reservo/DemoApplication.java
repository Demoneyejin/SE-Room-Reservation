package com.reservo.reservo;

import com.reservo.reservo.Models.Room;
import com.reservo.reservo.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private RoomRepository roomRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		roomRepository.save(new Room("GL 123", 4, true));
		roomRepository.save(new Room("ECS 222", 5, true));
	}


}
