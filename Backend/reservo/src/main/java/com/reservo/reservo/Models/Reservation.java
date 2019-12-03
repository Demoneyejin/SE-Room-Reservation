package com.reservo.reservo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document
public class Reservation {

	@Id
	private String reservationID;
	private String ownerID;
	private String roomID;
	private LocalTime time;
	private LocalDate date;

	public Reservation(String ownerID, String roomID, LocalTime time, LocalDate date){
		this.ownerID = ownerID;
		this.roomID = roomID;
		this.time = time;
		this.date = date;
	}


	public String getOwnerID() {
		return ownerID;
	}

	public String getRoomID() {
		return roomID;
	}

	public LocalTime getTime() {
		return time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setRoomID(String roomID){this.roomID = roomID;}

	public String getReservationID() {
		return reservationID;
	}
}