package com.reservo.reservo.ReturnClasses;

import com.reservo.reservo.Models.Reservation;

import java.util.List;

public class CurrentReservationsReturn {
    /*public date: string,
		public time: string,
		public room: string,
		public owner: string,
		public id: number,
		public roles: Roles[]*/

    private String date;
    private String time;
    private String room;
    private String owner;
    private String resID;
    private List<RoleReturn> roles;

    public CurrentReservationsReturn(String date, String time, String roomName, String owner, String resID){
        this.date = date;
        this.time = time;
        this.room = roomName;
        this.owner = owner;
        this.resID = resID;
    }

    public CurrentReservationsReturn(Reservation reservation, List<RoleReturn> roles){
        this.date = reservation.getDate().toString();
        this.time = reservation.getTime().toString();
        this.resID = reservation.getRoomID();
        this.owner = reservation.getOwnerID();
        this.room = reservation.getRoomID();
        this.roles = roles;
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public String getOwner() {
        return owner;
    }

    public String getResID() {
        return resID;
    }

    public void addRoles(List<RoleReturn> roleReturns){this.roles = roleReturns;}
}
