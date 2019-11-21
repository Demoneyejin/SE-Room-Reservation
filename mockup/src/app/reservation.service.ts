import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Reservation } from './view-reservation/Reservation';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private url = '/assets/data/reservations.json';

  private postURL = '/remove/reserve/';

  constructor(private http: HttpClient) { }

  getReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.url)
                  .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server Error');
  }

  removeReservation(reserveID, credentials): Observable<string> {
    // return this.http.post<string>(this.postURL + reserveID, credentials);
    return new Observable<string>(subscriber => {
      setTimeout(() => {
        subscriber.next('Confirmed');
        subscriber.complete();
      }, 2000);
    });
  }

  makeReservation(date: string, time: string, room: string){
    return new Observable<string>(subscriber => {
      setTimeout(() => {
        subscriber.next('Confirmed');
        subscriber.complete();
      }, 2000);
    });
  }
}
