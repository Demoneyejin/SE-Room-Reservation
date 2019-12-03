import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../User';
import { PleaseWaitComponent } from '../please-wait/please-wait.component';
import { CheckCredentialsService } from '../check-credentials.service';
import { OperationSuccessfulComponent } from '../operation-successful/operation-successful.component';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  securityQuestions = [
    {
      value: 'What is your mother\'s maiden name?',
      text: 'What is your mother\'s maiden name?'
    },
    {
      value: 'What is your first pet\'s name?',
      text: 'What is your first pet\'s name?'
    },
    {
      value: 'Where were you born?',
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

  constructor(private router: Router, private dialog: MatDialog, private signupService: CheckCredentialsService) { }

  refName = 'dashboard';

  ngOnInit() {
  }

  onClick() {

    console.log('Testing');

    if (this.profileForm.valid) { 
      const newUser: User = {
        name: this.profileForm.get('fullName').value,
        email: this.profileForm.get('email').value,
        password: this.profileForm.get('password').value,
        question: this.profileForm.get('securityQuestion').value,
        answer: this.profileForm.get('securityAnswer').value,
        username: this.profileForm.get('userName').value
      };

      const dialogRef = this.dialog.open(PleaseWaitComponent, {
        width: '350px',
        data: {text: 'We are currently adding your account to the system.'}
      });
      this.signupService.createUser(newUser).subscribe(
        data => {
          dialogRef.close();
          this.dialog.open(OperationSuccessfulComponent, {
            width: '350px',
            data: {text: 'Your user has successfully been added to the system', toDashboard: true}});
          },
        error => {
          dialogRef.close();
          console.log(error)
          this.dialog.open(OperationSuccessfulComponent, {
            width: '350px',
            data: {text: 'We could not add in your user to the system.', title: 'Error'}
          });
        });
    } else {
      console.log('Not vaild form');
    }

  }

  toLogin() {
    this.router.navigate(['/login']);
  }

  getNumQuestions() {
    return this.securityQuestions.length;
  }

}
