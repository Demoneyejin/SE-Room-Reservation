import { Component, OnInit, Input } from '@angular/core';
import { ModalService } from '../_modal';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  @Input() nextPage:string;

  

  constructor(private modalService: ModalService) { }

  ngOnInit() {
  }

  closeClick(){
    this.modalService.close('dialog-modal');
    window.location.href="./dashboard";
  }

}
