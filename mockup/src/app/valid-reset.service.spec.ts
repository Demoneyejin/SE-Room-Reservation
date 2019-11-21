import { TestBed } from '@angular/core/testing';

import { ValidResetService } from './valid-reset.service';

describe('ValidResetService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ValidResetService = TestBed.get(ValidResetService);
    expect(service).toBeTruthy();
  });
});
