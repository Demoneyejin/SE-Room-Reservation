import { Component, OnInit } from '@angular/core';
import { DialogComponent } from '../dialog/dialog.component';
import { faFileWord } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private router: Router) { }

  refName = "dashboard";

  ngOnInit() {
  }

  onClick(){

    //this.modalService.open('dialog-modal');
    window.location.href="./dashboard"
  }

  toLogin(){
    this.router.navigate(['/login']);
  }

}
