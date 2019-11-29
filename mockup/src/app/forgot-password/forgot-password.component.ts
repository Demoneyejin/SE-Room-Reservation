import { Component, OnInit } from '@angular/core';
import { GetQuestionsService } from '../get-questions.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  public secQues;
  public errorMsg;

  constructor(private questionService: GetQuestionsService, private router: Router) { }

  forgotPassForm = new FormGroup({
    username: new FormControl('', Validators.required),
    securQues: new FormControl('', Validators.required),
    secAns: new FormControl('', Validators.required)
  });

  ngOnInit() {
    this.questionService.getQuestions()
      .subscribe(data => this.secQues = data,
        error => this.errorMsg = error);
  }

  changePassword() {
    console.log('Pressed the button');
    if (this.forgotPassForm.valid) {
      this.router.navigate(['changepassword']);
    }
  }

}
