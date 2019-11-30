import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Opening } from './Openings';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GetOpenRoomsService {

  private url = 'http://localhost:8080/reserve';

  constructor(private http: HttpClient) { }

  getOpen(date: string, time: string, numPeople: number): Observable<Opening[]> {
    return this.http.get<Opening[]>(this.url + '/' + date + '/' + time + '/' + numPeople as string)
          .pipe(
            retry(3),
            catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server Error');
  }
}
