import { Component, OnInit, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private router: Router) { }

  loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  loginAttempts = 5;

  ngOnInit() {
  }

  onClick() {
    if (this.loginForm.valid) {
      this.router.navigate(['dashboard']);
    }
  }

  resolved(captchaResponse: string) {
    console.log(captchaResponse);
  }

  doCaptcha(): boolean {
    return this.loginAttempts > 4;
  }

}
