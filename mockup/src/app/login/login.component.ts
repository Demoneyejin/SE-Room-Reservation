import { Component, OnInit, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CheckCredentialsService } from '../check-credentials.service';
import { LoginReturn, UserInfo } from '../LoginReturn';
import { User } from '../User';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private router: Router, public checkAuth: CheckCredentialsService) { }

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  loginAttempts = 0;

  public loginInfo: LoginReturn = {
    loginAttempts: 0,
    loginSuccessful: true,
    sessionID: ''
  };

  ngOnInit() {
  }

  onClick() {
    console.log('onClick');
    if (this.loginForm.valid) {
      console.log('isValid');
      const user: UserInfo = {
        email: this.loginForm.get('username').value,
        password: this.loginForm.get('password').value
      };
      this.checkAuth.checkCredentials(user)
      .subscribe(
        data => {
          this.loginInfo = data;
          this.toTransfer();
        }
      );
    }
  }

  resolved(captchaResponse: string) {
    console.log(captchaResponse);
  }

  doCaptcha(): boolean {
    return this.loginAttempts > 4;
  }

  toTransfer() {
    if (this.loginInfo.loginSuccessful) {
      this.router.navigate(['dashboard']);
    }
  }

}
