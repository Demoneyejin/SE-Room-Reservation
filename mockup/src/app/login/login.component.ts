import { Component, OnInit, NgModule } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onEnter(){
    this.router.navigate(['dashboard']);
  }

  onClick(){
    this.router.navigate(['dashboard']);
  }

}
