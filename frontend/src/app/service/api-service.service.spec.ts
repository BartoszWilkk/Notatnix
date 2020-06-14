import { TestBed } from '@angular/core/testing';

import { ApiServiceService } from './api-service.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

describe('ApiServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule, RouterTestingModule]
  }));

  it('should be created', () => {
    const service: ApiServiceService = TestBed.get(ApiServiceService);
    expect(service).toBeTruthy();
  });
});
