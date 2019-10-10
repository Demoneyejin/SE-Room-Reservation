import { Component, OnInit } from '@angular/core';
import { GetQuestionsService } from '../get-questions.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  public secQues;
  public errorMsg;

  constructor(private questionService: GetQuestionsService, private router: Router) { }

  ngOnInit() {
    this.questionService.getQuestions()
      .subscribe(data => this.secQues = data,
        error => this.errorMsg = error);
  }

  changePassword() {
    console.log('Pressed the button');
    this.router.navigate(['changepassword']);
  }

}