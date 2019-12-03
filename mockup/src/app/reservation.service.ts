import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Reservation } from './view-reservation/Reservation';
import { catchError } from 'rxjs/operators';
import { RoleRequest } from './view-reservation/view-reservation.component';
import { Roles } from './view-reservation/Roles';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private url = 'http://localhost:8080/reserve/uname';

  private removeReservationURL = 'http://localhost:8080/reserve/';

  private roleURL = 'http://localhost:8080/reserve/role/add';

  private makeResURL = 'http://localhost:8080/reserve/make';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient) { }

  getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.url)
                  .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server Error');
  }

  removeReservation(reserveID): Observable<string> {
    return this.http.delete<string>(this.removeReservationURL + reserveID + '/remove')
      .pipe(catchError(this.errorHandler));
  }

  makeReservation(date: string, time: string, room: string) {
    return this.http.post<Reservation>(this.makeResURL, {date, time, room, username: 'uname'}, this.httpOptions)
      .pipe(catchError(this.errorHandler));
  }

  addRole(role: RoleRequest) {

    return this.http.post<Roles>(this.roleURL, role, this.httpOptions)
                    .pipe(catchError(this.errorHandler));
  }
}
