import { Component, OnInit, Inject } from '@angular/core';
import { ReservationService } from '../reservation.service';
import { Reservation } from './Reservation';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { stringToKeyValue } from '@angular/flex-layout/extended/typings/style/style-transforms';

@Component({
  selector: 'app-view-reservation',
  templateUrl: './view-reservation.component.html',
  styleUrls: ['./view-reservation.component.css']
})
export class ViewReservationComponent implements OnInit {

  public reservations: Reservation[];
  public errorMsg;

  public noReservations: boolean;

  public confirmedCancellation: boolean;

  constructor(private reservationService: ReservationService, private dialog: MatDialog) { }

  ngOnInit() {
    this.reservationService.getReservations()
        .subscribe(data => {this.reservations = data;
                            this.noReservations = this.reservations.length === 0; },
        error => this.errorMsg = error);
  }

  onClick(reserve: Reservation) {
    const dialogRef = this.dialog.open(ReservationDialogComponent, {
      width: '350px',
      data: {date: reserve.date}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('Dialog returned: ' + result);
      this.afterDialog(result, reserve);
    });

  }

  afterDialog(selected: string, reservation: Reservation) {

    if (selected === 'Confirmed') {
      const dialogRef = this.dialog.open(ReservationCancelWaitComponent, {
        width: '350px'
      });
      this.reservationService.removeReservation(reservation.id, 'creds').subscribe(data => {
        console.log('Dialog finished');
        this.confirmedCancellation = data === 'Confirmation';
        dialogRef.close();
      });
    }

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

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<ReservationDialogComponent>) {}

  onConfirm() {
    this.dialogRef.close('Confirmed');
  }

  onCancel() {
    this.dialogRef.close('Cancelled');
  }

}

@Component({
  selector: 'app-reservation-cancel-wait',
  templateUrl: 'reservation-cancel-wait.html',
  styles: ['h2 {font-family: \'Bitter\', serif}',
  '.content {font-family: Roboto;}']
})
export class ReservationCancelWaitComponent implements OnInit {
  ngOnInit(): void {

  }
}
