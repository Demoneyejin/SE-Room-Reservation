import { Component, OnInit, Input } from '@angular/core';
import { Opening } from '../Openings';
import { GetOpenRoomsService } from '../get-open-rooms.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ReservationService } from '../reservation.service';
import { PleaseWaitComponent } from '../please-wait/please-wait.component';
import { MatDialog } from '@angular/material/dialog';
import { OperationSuccessfulComponent } from '../operation-successful/operation-successful.component';

@Component({
  selector: 'app-open-room-list',
  templateUrl: './open-room-list.component.html',
  styleUrls: ['./open-room-list.component.css']
})
export class OpenRoomListComponent implements OnInit {
 openings: Opening[];

  constructor(private openingService: GetOpenRoomsService,
              private reservationService: ReservationService, public dialog: MatDialog) { }

  public is404 = false;


  ngOnInit() {
    this.openingService.getOpen().subscribe(
      data => this.openings = data,
      error => this.is404 = true
    );

  }

  errorParse(error: HttpErrorResponse) {
    console.log('testing ' + error.message);
    if (error.status === 404) {
      this.is404 = true;
    }
  }

  testMethod(){
    console.log('TEst');
  }

  reserve(opening: Opening) {
    console.log('Testing');
    const dialogRef = this.dialog.open(PleaseWaitComponent, {
      width: '350px',
      data: {text: 'We are confirming your reservation'},
      disableClose: true
    });
    this.reservationService.makeReservation(opening.date, opening.time.toString(), opening.roomName).subscribe(
      data => {
        dialogRef.close();
        this.dialog.open(OperationSuccessfulComponent, {
          width: '350px',
          data: {text: 'Your reservation for ' + opening.date + ' at ' + opening.roomName + '  has been confirmed.'}
        });
      }
    );
  }

}
