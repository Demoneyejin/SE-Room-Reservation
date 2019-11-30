import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { User } from './User';

@Injectable({
  providedIn: 'root'
})
export class CreateUserService {

  private url = 'http://localhost:8080/user/create';

  constructor(private http: HttpClient) { }

  public createUser(user: User): Observable<string> {
    return this.http.post<string>(this.url, user)
      .pipe(
        retry(3),
        catchError(this.errorHandler)
      );
  }

  private errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server Error');
  }
}
