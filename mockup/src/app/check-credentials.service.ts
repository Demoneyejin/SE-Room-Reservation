import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CheckCredentialsService {

  private url = '';

  constructor(private http: HttpClient) { }

  checkCredentials(credentials: string): Observable<string> {
    return this.http.post<string>(this.url, credentials);
  }

}
