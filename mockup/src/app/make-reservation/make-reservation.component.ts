import { Component, OnInit, ViewChild, ComponentFactoryResolver, OnDestroy } from '@angular/core';
import { ReserveListDirective } from '../reserve-list.directive';
import { OpenRoomListComponent } from '../open-room-list/open-room-list.component';
import { Opening } from '../Openings';
import { GetOpenRoomsService } from '../get-open-rooms.service';

@Component({
  selector: 'app-make-reservation',
  templateUrl: './make-reservation.component.html',
  styleUrls: ['./make-reservation.component.css']
})
export class MakeReservationComponent implements OnInit {
  @ViewChild(ReserveListDirective, {static: true}) reserveHost: ReserveListDirective;
  constructor(private componentFactoryResolver: ComponentFactoryResolver, private openingService: GetOpenRoomsService) { }

  minDate = new Date();

  times = [
    {
      value: 900,
      text: '9:00 am'
    },
    {
      value: 1000,
      text: '10:00 am'
    },
    {
      value: 1100,
      text: '11:00 am'
    },
    {
      value: 1200,
      text: '12:00 pm'
    },
    {
      value: 1300,
      text: '1:00 pm'
    },
    {
      value: 1400,
      text: '2:00 pm'
    },
    {
      value: 1500,
      text: '3:00 pm'
    },
    {
      value: 1600,
      text: '4:00 pm'
    },
    {
      value: 1700,
      text: '5:00 pm'
    },
  ];

  numPersons = [
    {
      value: 2,
      text: '2'
    },
    {
      value: 3,
      text: '3'
    },
    {
      value: 4,
      text: '4'
    },
    {
      value: 5,
      text: '5'
    },
    {
      value: 6,
      text: '6'
    },
    {
      value: 7,
      text: '7'
    },
    {
      value: 6,
      text: '6'
    },
    {
      value: 7,
      text: '7'
    },
    {
      value: 8,
      text: '8'
    },
    {
      value: 9,
      text: '9'
    },
    {
      value: 10,
      text: '10+'
    }
  ];

  ngOnInit() {

  }

  loadComponent(opens: Opening[]) {
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(OpenRoomListComponent);

    const viewContainerRef = this.reserveHost.viewContainerRef;

    viewContainerRef.clear();

    console.log(opens);

    const compoentRef = viewContainerRef.createComponent(componentFactory);
    (compoentRef.instance as OpenRoomListComponent).openings = opens;
  }

  getAvailableRooms() {
    let errorMsg = '';
    let openings: Opening[];

    this.openingService.getOpen()
      .subscribe(data => {openings = data; console.log(openings); },
          error => errorMsg = error);

    console.log(openings);

    this.loadComponent(openings);
  }


}
