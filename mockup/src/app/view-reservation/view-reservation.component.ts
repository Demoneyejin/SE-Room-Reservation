import { Component, OnInit } from '@angular/core';
import { Reservation } from './Reservation'

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.css']
})
export class ViewReservationComponent implements OnInit {

  reservations = [
    new Reservation("9/18/19", "12:00pm", "Green Library 245", "dmato"),
    new Reservation("9/31/19", "5:30pm", "Green Library 512", "dmato")
  ];

  constructor() { }

  ngOnInit() {
  }

}
