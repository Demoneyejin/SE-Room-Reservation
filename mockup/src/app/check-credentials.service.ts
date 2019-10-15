import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginReturn } from './LoginReturn';

@Injectable({
  providedIn: 'root'
})
export class CheckCredentialsService {

  private url = '';

  constructor(private http: HttpClient) { }

  checkCredentials(credentials: string): Observable<LoginReturn> {
    return this.http.post<LoginReturn>(this.url, credentials);
  }

}
