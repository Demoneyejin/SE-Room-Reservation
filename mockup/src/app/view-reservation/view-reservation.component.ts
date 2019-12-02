import { Component, OnInit, Inject } from '@angular/core';
import { ReservationService } from '../reservation.service';
import { Reservation } from './Reservation';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { OperationSuccessfulComponent } from '../operation-successful/operation-successful.component';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Roles } from './Roles';

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
                            this.noReservations = this.reservations.length === 0;
                            console.log(this.reservations); },
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
      this.reservationService.removeReservation(reservation.resID, 'creds').subscribe(data => {
        console.log('Dialog finished');
        this.confirmedCancellation = data === 'Confirmation';
        dialogRef.close();
        this.dialog.open(OperationSuccessfulComponent, {
          width: '350px',
          data: {text: 'You\'re reservation for ' + reservation.date + ' has been successfully been cancelled.',
                toDashboard: false}
        });
      });
    }

  }

  addRoles(reservation: Reservation) {
    const dialogRef = this.dialog.open(AssignUserRoleComponent, {
      width: '350px'
    });

    dialogRef.afterClosed().subscribe(result => {

      const roleRequest: RoleRequest = {
        reservationID: reservation.resID,
        role: result.role,
        userID: result.email
      };

      this.reservationService.addRole(roleRequest).subscribe(
        data => {
        }
      );

    });

  }
  addRoletoReservation(reservation: Reservation, roleRequest: RoleRequest) {
      for (let i = 0; i < reservation.roles.length; i++) {
        if (reservation.roles[i].email === roleRequest.userID) {
          reservation.roles[i].roles = roleRequest.role;
          break;
        } else if (i === reservation.roles.length - 1) {
          reservation.roles.push({roles: roleRequest.role, email: roleRequest.userID});
        }
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

@Component({
  selector: 'app-assign-role-dialog',
  templateUrl: 'assign-role.html',
  styles: ['./assign-role.css']
})
export class AssignUserRoleComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<AssignUserRoleComponent>) {}

  roleForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    role: new FormControl('', Validators.required)
  });

  ngOnInit(): void {
  }

  closeDialog() {
    if (this.roleForm.valid) {
      const email = this.roleForm.get('email').value;
      const role = this.roleForm.get('role').value;

      this.dialogRef.close({email, role});

    }
  }
}

export interface RoleRequest {
  reservationID: string;
  role: string;
  userID: string;
}
