import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OperationSuccessfulComponent } from './operation-successful.component';

describe('OperationSuccessfulComponent', () => {
  let component: OperationSuccessfulComponent;
  let fixture: ComponentFixture<OperationSuccessfulComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OperationSuccessfulComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OperationSuccessfulComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
