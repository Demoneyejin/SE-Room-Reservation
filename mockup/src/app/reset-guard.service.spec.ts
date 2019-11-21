import { TestBed } from '@angular/core/testing';

import { ResetGuardService } from './reset-guard.service';

describe('ResetGuardService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ResetGuardService = TestBed.get(ResetGuardService);
    expect(service).toBeTruthy();
  });
});
