import { Component, OnInit, Inject } from '@angular/core';
import { ReservationService } from '../reservation.service';
import { Reservation } from './Reservation';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.css']
})
export class ViewReservationComponent implements OnInit {

  public reservations;
  public errorMsg;

  constructor(private reservationService: ReservationService, private dialog: MatDialog) { }

  ngOnInit() {
    this.reservationService.getReservations()
        .subscribe(data => this.reservations = data,
          error => this.errorMsg = error);
  }

  onClick(reserve: Reservation) {
    let dialogRef = this.dialog.open(ReservationDialogComponent, {
      width: '350px',
      data: {date: reserve.date}
    });
  }

}

@Component({
  selector: 'app-reservation-dialog',
  templateUrl: 'reservation-dialog.html',
  styles: ['h2 { font-family: \'Bitter\', serif}',
  '.content {font-family: Roboto;}']
})

export class ReservationDialogComponent implements OnInit {
  ngOnInit(): void {
  }

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

}
