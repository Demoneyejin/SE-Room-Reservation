import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CreateUserService {

  constructor() { }

  public createUser(): Observable<boolean> {
    return new Observable<boolean>(subscriber => {
      setTimeout(() => {
        subscriber.next(true);
        subscriber.complete();
      }, 2000);
    });
  }
}
