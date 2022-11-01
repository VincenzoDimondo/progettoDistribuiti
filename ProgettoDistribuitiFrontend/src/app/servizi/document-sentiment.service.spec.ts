import {TestBed} from '@angular/core/testing';

import {DocumentSentimentService} from './document-sentiment.service';

describe('OrologioService', () => {
  let service: DocumentSentimentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentSentimentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
