import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ValidResetService {

  private isValid: boolean;

  constructor() { }

  setIsValid() {
    this.isValid = true;
  }

  getIsValid() {
    this.isValid = false;
  }

}
