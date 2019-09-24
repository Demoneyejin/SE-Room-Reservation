import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CreateUserService {

  constructor() { }

  public createUser(): number {
    setTimeout(() => {
    },
    25000);
    return 1;
  }
}
