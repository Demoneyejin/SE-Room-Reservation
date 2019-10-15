import { TestBed } from '@angular/core/testing';

import { CheckCredentialsService } from './check-credentials.service';

describe('CheckCredentialsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CheckCredentialsService = TestBed.get(CheckCredentialsService);
    expect(service).toBeTruthy();
  });
});
