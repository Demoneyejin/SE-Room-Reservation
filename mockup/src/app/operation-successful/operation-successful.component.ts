import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-operation-successful',
  templateUrl: './operation-successful.component.html',
  styleUrls: ['./operation-successful.component.css']
})
export class OperationSuccessfulComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public router: Router) {}

  public title = 'Operation Successful';

  ngOnInit() {
    if (this.data.title) {
      this.title = this.data.title;
    }
  }

  onClick() {
    if (this.data.toDashboard) {
      this.router.navigate(['dashboard']);
    }
  }

}
