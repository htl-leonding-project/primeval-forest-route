import { TestBed } from '@angular/core/testing';

import { QuarkusBackendService } from './quarkus-backend.service';

describe('QuarkusBackendService', () => {
  let service: QuarkusBackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuarkusBackendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
