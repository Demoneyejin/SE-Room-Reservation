import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { LoginReturn, UserInfo } from './LoginReturn';
import { retry, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CheckCredentialsService {

  private url = 'http://localhost:8080/user/auth';

  private createURL = 'http://localhost:8080/user/signup';

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

  constructor(private http: HttpClient) { }

  checkCredentials(user: UserInfo): Observable<LoginReturn> {
    return this.http.post<LoginReturn>(this.url, user)
            .pipe(
              retry(3)
              );
  }

  createUser(user: any) {
    return this.http.post<string>(this.createURL, user, this.httpOptions)
      .pipe(catchError(this.errorHandler));
  }

  isAuthenticated(): boolean {
    return true;
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.message || 'Server Error');
  }

}
