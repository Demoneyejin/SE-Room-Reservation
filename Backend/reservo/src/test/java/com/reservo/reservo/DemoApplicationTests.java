package com.reservo.reservo;

import com.reservo.reservo.Models.Reservation;
import com.reservo.reservo.Models.Roles;
import com.reservo.reservo.Models.Room;
import com.reservo.reservo.Models.User;
import com.reservo.reservo.Repository.*;
import com.reservo.reservo.Services.MongoUserDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReservationRepository reservationRepository;

	@MockBean
	private RoomRepository roomRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RolesRepository rolesRepository;

	@MockBean
	private MongoUserDetailService userService;

	@Test
	public void testGetReservation()
		throws Exception {
		String uid  = "testUID";

		List<Reservation> reservationTest = new ArrayList<>(Arrays.asList(
				new Reservation(uid, "testRoom1",LocalTime.now(), LocalDate.now()),
				new Reservation(uid, "testRoom2", LocalTime.now(), LocalDate.now())
		));

		List<Roles> roles1 = new ArrayList<>(Arrays.asList(
				new Roles(reservationTest.get(0).getReservationID(), uid, new ArrayList<>(Arrays.asList("Role 1", "Role 2"))),
				new Roles(reservationTest.get(0).getReservationID(), uid, new ArrayList<>(Arrays.asList("Role 3", "Role 4")))
		));

		List<Roles> roles2 = new ArrayList<>(Arrays.asList(
				new Roles(reservationTest.get(1).getReservationID(), uid, new ArrayList<>(Arrays.asList("Role 5", "Role 6"))),
				new Roles(reservationTest.get(1).getReservationID(), uid, new ArrayList<>(Arrays.asList("Role 7", "Role 8")))
		));

		User user = new User();
		user.setUserID(uid);
		user.setUserName(uid);

		when(reservationRepository.findByOwnerID(uid)).thenReturn(reservationTest);
		when(roomRepository.findById("testRoom1")).thenReturn(java.util.Optional.of(new Room("Room1", 1, true)));
		when(roomRepository.findById("testRoom2")).thenReturn(java.util.Optional.of(new Room("Room2", 2, true)));
		when(userRepository.findById(uid)).thenReturn(java.util.Optional.of(user));
		when(rolesRepository.findByReservationID(reservationTest.get(0).getReservationID())).thenReturn(roles1);
		when(rolesRepository.findByReservationID(reservationTest.get(1).getReservationID())).thenReturn(roles2);

		verify(userRepository, never()).findById(uid);

		this.mockMvc.perform(get("/reserve/" + uid)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].date").value(reservationTest.get(0).getDate().toString()))
				.andExpect(jsonPath("$[0].time").value(reservationTest.get(0).getTime().toString()))
				.andExpect(jsonPath("$[0].room").value("Room1"))
				.andExpect(jsonPath("$[0].owner").value(uid))
				.andExpect(jsonPath("$[0].resID").value(reservationTest.get(0).getReservationID()))
				.andExpect(jsonPath("$[1].date").value(reservationTest.get(1).getDate().toString()))
				.andExpect(jsonPath("$[1].time").value(reservationTest.get(1).getTime().toString()))
				.andExpect(jsonPath("$[1].room").value("Room2"))
				.andExpect(jsonPath("$[1].owner").value(uid))
				.andExpect(jsonPath("$[1].resID").value(reservationTest.get(1).getReservationID()));
	}

	@Test
	public void testGetRooms() throws Exception{
		when(roomRepository.findAll()).thenReturn(Arrays.asList(
			new Room("Room1", 2, true),
			new Room("Room2", 2, true)
		));

		this.mockMvc.perform(get("/reserve/rooms/all")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].roomName").value("Room1"))
				.andExpect(jsonPath("$[0].capacity").value(2))
				.andExpect(jsonPath("$[0].available").value(true))
				.andExpect(jsonPath("$[1].roomName").value("Room2"))
				.andExpect(jsonPath("$[1].capacity").value(2))
				.andExpect(jsonPath("$[1].available").value(true));

	}

	@Test
	public void testGetOpenings() throws Exception {

		List<Room> rooms = Arrays.asList(
				new Room("Room1", 5, true),
				new Room("Room3", 3, false),
				new Room("Room4", 10, true)
		);

		rooms.get(0).setRoomID("RoomID1");
		rooms.get(1).setRoomID("RoomID2");
		rooms.get(2).setRoomID("RoomID3");

		List<String> roomIDs = rooms.stream().map(Room::getRoomID).collect(Collectors.toList());

		List<Reservation> reservations = Collections.singletonList(
				new Reservation("testUser", rooms.get(2).getRoomID(), LocalTime.parse("12:00"), LocalDate.parse("2019-12-12"))
		);

		roomIDs.forEach(System.out::println);

		when(roomRepository.findByCapacityGreaterThanEqual(3)).thenReturn(rooms);
		when(reservationRepository.findByTimeAndDateAndRoomIDIn(LocalTime.parse("12:00"), LocalDate.parse("2019-12-12"), roomIDs)).thenReturn(reservations);

		this.mockMvc.perform(get("/reserve/2019-12-12/12:00/3")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].date").value("2019-12-12"))
				.andExpect(jsonPath("$[0].time").value("12:00"))
				.andExpect(jsonPath("$[0].room").value("Room1"))
				.andExpect(jsonPath("$[0].capacity").value(5))
				.andExpect(jsonPath("$[1].date").value("2019-12-12"))
				.andExpect(jsonPath("$[1].time").value("12:00"))
				.andExpect(jsonPath("$[1].room").value("Room3"))
				.andExpect(jsonPath("$[1].capacity").value(3));

	}

	@Test
	public void createUserTest() throws Exception{

		Map<String, String> newUser = new HashMap<>();
		newUser.put("username", "newUserName");
		newUser.put("name", "Name");
		newUser.put("password", "password");
		newUser.put("email", "email@email.com");
		newUser.put("question", "What's a thing?");
		newUser.put("answer", "A thing");

		when(this.userService.findUserByUsername(newUser.get("username")))
				.thenReturn(false);

		this.mockMvc.perform(post("/user/signup", newUser)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].username").value(newUser.get("username")))
				.andExpect(jsonPath("$[0].name").value(newUser.get("name")))
				.andExpect(jsonPath("$[0].password").value(newUser.get("password")))
				.andExpect(jsonPath("$[0].email").value(newUser.get("email")));


	}

}
