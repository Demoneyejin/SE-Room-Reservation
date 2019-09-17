import { Component, OnInit } from '@angular/core';
import { DialogComponent } from '../dialog/dialog.component';
import { ModalService } from '../_modal';
import { faFileWord } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private modalService: ModalService) { }

  refName = "dashboard";

  ngOnInit() {
  }

  onClick(){

    //this.modalService.open('dialog-modal');
    window.location.href="./dashboard"
  }

}
