import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginReturn } from './LoginReturn';
import { retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CheckCredentialsService {

  private url = '';

  constructor(private http: HttpClient) { }

  checkCredentials(credentials: string): Observable<LoginReturn> {
    return this.http.post<LoginReturn>(this.url, credentials)
            .pipe(
              retry(3)
              );
  }

  isAuthenticated(): boolean {
    return false;
  }

}
