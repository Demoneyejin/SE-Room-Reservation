import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CreateUserService } from '../create-user.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  securityQuestions = [
    {
      value: 1,
      text: 'What is your mother\'s maiden name?'
    },
    {
      value: 2,
      text: 'What is your first pet\'s name?'
    },
    {
      value: 3,
      text: 'Where were you born?'
    }
  ];

  profileForm = new FormGroup({
    userName: new FormControl('', Validators.required),
    fullName: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.email, Validators.required]),
    submit: new FormControl(''),
    password: new FormControl('', Validators.required),
    confirmPassword: new FormControl('', Validators.required),
    securityQuestion: new FormControl('', Validators.required),
    securityAnswer: new FormControl('', Validators.required)
  });

  constructor(private router: Router, private dialog: MatDialog, private signupService: CreateUserService) { }

  refName = 'dashboard';

  ngOnInit() {
  }

  onClick() {

    console.log('Testing');

    if (this.profileForm.valid){
      let dialogRef = this.dialog.open(SignupDialogComponent, {
        width: '350px',
        disableClose: true
      });
    }

  }

  toLogin() {
    this.router.navigate(['/login']);
  }

  getNumQuestions() {
    return this.securityQuestions.length;
  }



}

@Component({
  selector: 'app-signup-dialog',
  styleUrls: ['signup-dialog.css'],
  templateUrl: 'signup-dialog-content.html'
})

export class SignupDialogComponent {

  addContinue = false;

  constructor(private router: Router, private createUserService: CreateUserService,
              public dialogRef: MatDialogRef<SignupDialogComponent> ) {
    createUserService.createUser().subscribe(data =>
      this.addContinue = data);
  }

  toDashboard() {
    this.dialogRef.close();
    this.router.navigate(['/dashboard']);
  }
}
