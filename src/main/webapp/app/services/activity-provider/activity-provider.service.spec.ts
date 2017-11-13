import { TestBed, inject } from '@angular/core/testing';

import { ActivityProviderService } from './activity-provider.service';

describe('ActivityProviderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ActivityProviderService]
    });
  });

  it('should be created', inject([ActivityProviderService], (service: ActivityProviderService) => {
    expect(service).toBeTruthy();
  }));
});
