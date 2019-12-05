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

  public pwLength = false;

  private password: string;

  changePW() {
    const pw = this.loginForm.get('password').value;
    if (pw.length < 8) {
      this.loginForm.get('password').setErrors({
        minLength: true
      });
      this.pwLength = true;
    } else {
      this.password = pw;
      this.loginForm.get('password').setValue('***');
      this.pwLength = false;
    }
  }

  ngOnInit() {
  }

  onClick() {
    if (this.loginForm.valid) {
      const user = {
        username: this.loginForm.get('username').value,
        password: this.password
      };
      this.checkAuth.checkCredentials(user).subscribe(
        data => {
          console.log('Got to the return of the subscribe');
          if (data.username) {
            sessionStorage.setItem('username', data.username);
            console.log('Returned the username');
            this.router.navigate(['dashboard']);
          } else {
            this.dialog.open(OperationSuccessfulComponent, {
              width: '350px',
              data: {text: data.error, title: 'Error'}
            });
          }
        }
      );
    }
  }

  resolved(captchaResponse: string) {
    console.log(captchaResponse);
  }

  doCaptcha(): boolean {
    return false;
  }

}
