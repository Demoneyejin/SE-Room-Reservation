import { TestBed } from '@angular/core/testing';

import { GetOpenRoomsService } from './get-open-rooms.service';

describe('GetOpenRoomsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GetOpenRoomsService = TestBed.get(GetOpenRoomsService);
    expect(service).toBeTruthy();
  });
});
