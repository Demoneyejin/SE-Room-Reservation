import { Component, OnInit } from '@angular/core';
import { Reservation } from './Reservation'
import { ReservationService } from '../reservation.service';

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.css']
})
export class ViewReservationComponent implements OnInit {

  public reservations;
  public errorMsg;

  constructor(private _reservationService: ReservationService) { }

  ngOnInit() {
    this._reservationService.getReservations()
        .subscribe(data => this.reservations = data,
          error => this.errorMsg = error);
    
  }

}
