import { Injectable } from '@angular/core';
import { ValidResetService } from './valid-reset.service';
import { Router, CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ResetGuardService implements CanActivate {

  constructor(public valid: ValidResetService, public router: Router) { }

  canActivate(): boolean {
    if (!this.valid.getIsValid) {
      this.router.navigate(['login']);
      return false;
    }
    return true;
  }
}
