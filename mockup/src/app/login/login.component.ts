import { Component, OnInit, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CheckCredentialsService } from '../check-credentials.service';
import { LoginReturn, UserInfo } from '../LoginReturn';
import { User } from '../User';
import { MatDialog } from '@angular/material/dialog';
import { OperationSuccessfulComponent } from '../operation-successful/operation-successful.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private router: Router, public checkAuth: CheckCredentialsService, public dialog: MatDialog) { }

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required])
  });

  loginAttempts = 0;

  public loginInfo: LoginReturn = {
    loginAttempts: 0,
    loginSuccessful: true,
    sessionID: ''
  };

  public pwLength = false;

  private password: string;

  changePW() {
    const pw = this.loginForm.get('password').value;
    if (pw.length < 8) {
      this.loginForm.get('password').setErrors({
        minLength: true
      });
      this.pwLength = true;
    }
    else {
      this.password = pw;
      this.loginForm.get('password').setValue('***');
      this.pwLength = false;
    }
  }

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
        },
        error => {
          this.dialog.open(OperationSuccessfulComponent, {
            width: '350px',
            data: {text: 'An error has occurred logging in. Please try again.', title: 'Login Error'}
          });
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
