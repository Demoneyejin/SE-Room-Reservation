import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Opening } from './Openings';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GetOpenRoomsService {

  private url = '/assets/data/openings.jso';

  constructor(private http: HttpClient) { }

  getOpen(): Observable<Opening[]> {
    return this.http.get<Opening[]>(this.url)
          .pipe(
            retry(3),
            catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server Error');
  }
}
