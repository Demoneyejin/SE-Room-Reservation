import { Component, OnInit } from '@angular/core';
import { GetQuestionsService } from '../get-questions.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  public secQues;
  public errorMsg;

  constructor(private questionService: GetQuestionsService) { }

  ngOnInit() {
    this.questionService.getQuestions()
      .subscribe(data => this.secQues = data,
        error => this.errorMsg = error);
  }

}
