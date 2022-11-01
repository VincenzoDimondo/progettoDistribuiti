import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import { AnalyzeSentimentResult, AnalyzeSentimentSuccessResult, DocumentSentimentLabel, TextAnalyticsClient } from '@azure/ai-text-analytics';
import {Observable} from 'rxjs';
import {tap} from "rxjs/operators";
import { DocumentAnalyzed } from '../model/document-analyzed';

@Injectable({
  providedIn: 'root'
})
export class DocumentSentimentService {

  constructor(private http: HttpClient) {
  }

  private DOCUMENT_SENTIMENT_URI = 'http://localhost:8080/document_sentiment';

  createDocumentSentiment(documentSentiment: any): Observable<any> {
    return this.http.post<any>(this.DOCUMENT_SENTIMENT_URI + "/create", documentSentiment);
  }

  getAllDocumentSentiment(): Observable<DocumentAnalyzed[]>{
    return this.http.get<DocumentAnalyzed[]>(this.DOCUMENT_SENTIMENT_URI + "/getAll");
  }

  removeDocumentSentiment(id: number): Observable<any>{
    return this.http.post<any>(this.DOCUMENT_SENTIMENT_URI + "/remove?id=" + id, null);
  }

  getDocumentSentiment(id: number): Observable<AnalyzeSentimentSuccessResult>{
    return this.http.get<AnalyzeSentimentSuccessResult>(this.DOCUMENT_SENTIMENT_URI + "/getById?id=" + id);
  }

  getSizeAllDocuments(): Observable<number>{
    return this.http.get<number>(this.DOCUMENT_SENTIMENT_URI + "/getSizeAll");
  }

  getDocumentByIdPageable(id: number, pageNumber: number, pageSize: number): Observable<any>{
    return this.http.get(this.DOCUMENT_SENTIMENT_URI + '/getByIdPaged?idDocument=' + id + '&pageNumber=' + pageNumber + '&pageSize=' + pageSize).pipe(tap( p => {}));
  }

  getAllDocumentSentimentPaged(pageNumber: number, pageSize: number): Observable<DocumentAnalyzed[]>{
    return this.http.get<DocumentAnalyzed[]>(this.DOCUMENT_SENTIMENT_URI + "/getAllPaged?pageNumber=" + pageNumber + "&pageSize=" + pageSize).pipe(tap( p => {}));
  }

}
