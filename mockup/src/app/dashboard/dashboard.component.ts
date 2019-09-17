import { Component, OnInit } from '@angular/core';
import {faCalendarPlus} from '@fortawesome/free-solid-svg-icons';
import { card } from './card';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  faCalendarPlus = faCalendarPlus

  cards = [
    new card("Make a Reservation", "calendar-plus", "pagenotfound"),
    new card("View Reservations", "calendar-alt", "reservations")
  ];

  constructor() { }

  ngOnInit() {
  }

  onclick(reference){
    window.location.href="./" + reference;
  }

}
