import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class AzureService {

  private endpoint = 'https://servizicognitivi.cognitiveservices.azure.com/';
  private url_azure = '/language/:analyze-text?api-version=2022-05-01';

  constructor(private http: HttpClient) {
  }

  getProducts(auth: string): Observable<any> {
    const myheader = new HttpHeaders().set('AUTH_TOKEN', auth);
    return this.http.get<any>(this.endpoint);
  }


}
