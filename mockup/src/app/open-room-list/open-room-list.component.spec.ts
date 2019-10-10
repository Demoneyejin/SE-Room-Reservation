import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenRoomListComponent } from './open-room-list.component';

describe('OpenRoomListComponent', () => {
  let component: OpenRoomListComponent;
  let fixture: ComponentFixture<OpenRoomListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenRoomListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenRoomListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
