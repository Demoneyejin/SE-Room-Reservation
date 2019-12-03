import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginReturn, UserInfo } from './LoginReturn';
import { retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CheckCredentialsService {

  private url = 'http://localhost:8080/user/auth';

  constructor(private http: HttpClient) { }

  checkCredentials(user: UserInfo): Observable<LoginReturn> {
    return this.http.post<LoginReturn>(this.url, user)
            .pipe(
              retry(3)
              );
  }

  isAuthenticated(): boolean {
    return true;
  }

}
