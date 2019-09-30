import { Component, OnInit } from '@angular/core';
import {faCalendarPlus} from '@fortawesome/free-solid-svg-icons';
import { card } from './card';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  faCalendarPlus = faCalendarPlus;

  cards = [
    new card('Make a Reservation', 'calendar-plus', 'makereservation'),
    new card('View Reservations', 'calendar-alt', 'reservations')
  ];

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onclick(reference){
    this.router.navigate([reference]);
  }

}
