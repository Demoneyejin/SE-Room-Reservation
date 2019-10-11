import { Component, OnInit, Input } from '@angular/core';
import { Opening } from '../Openings';
import { GetOpenRoomsService } from '../get-open-rooms.service';

@Component({
  selector: 'app-open-room-list',
  templateUrl: './open-room-list.component.html',
  styleUrls: ['./open-room-list.component.css']
})
export class OpenRoomListComponent implements OnInit {
  @Input() openings: Opening[];

  constructor(private openingService: GetOpenRoomsService) { }

  private errorMsg;

  ngOnInit() {
    this.openingService.getOpen().subscribe(
      data => this.openings = data,
      error => this.errorMsg = error
    );

  }

}
