import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.css']
})
export class DialogComponent implements OnInit {

  @Input() nextPage:string;

  

  constructor() { }

  ngOnInit() {
  }

  closeClick(){
    window.location.href="./dashboard";
  }

}
