import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Reservation } from './view-reservation/Reservation';
import { catchError } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private _url: string = "/assets/data/reservations.json"

  constructor(private http: HttpClient) { }

  getReservations(): Observable<Reservation[]>{
    return this.http.get<Reservation[]>(this._url)
                  .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse){
    return throwError(error.message || "Server Error");
  }
}
