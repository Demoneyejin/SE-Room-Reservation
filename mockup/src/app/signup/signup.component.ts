import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateUserService } from '../create-user.service';


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

  constructor(private router: Router, private dialog: MatDialog, private signupService: CreateUserService) { }

  refName = 'dashboard';

  ngOnInit() {
  }

  onClick() {

    console.log('Testing');

    let dialogRef = this.dialog.open(SignupDialogComponent, {
      width: '350px'
    });
    /*if (this.signupService.createUser()) {
      dialogRef.close();
      this.router.navigate(['/dashboard']);
    }*/

  }

  toLogin() {
    this.router.navigate(['/login']);
  }

  getNumQuestions() {
    return this.securityQuestions.length;2
  }

}

@Component({
  selector: 'app-signup-dialog',
  styleUrls: ['signup-dialog.css'],
  templateUrl: 'signup-dialog-content.html'
})

export class SignupDialogComponent {
  constructor() {}
}
