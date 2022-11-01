import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { DocumentAnalyzed } from '../model/document-analyzed';
import { AuthService } from '../servizi/auth.service';
import { DocumentSentimentService } from '../servizi/document-sentiment.service';

@Component({
  selector: 'app-elenco',
  templateUrl: './elenco.component.html',
  styleUrls: ['./elenco.component.css']
})
export class ElencoComponent implements OnInit {

  numPage: number = 0;
  numElementi: number = 4;
  lengthDataset = 0;
  DATASET: DocumentAnalyzed[] = [];

  @Output() elementoEmitter = new EventEmitter<DocumentAnalyzed>() ;
  elementoDaPassare!: DocumentAnalyzed;

  constructor(private documentSentimentService: DocumentSentimentService, private router: Router, private authService: AuthService) { /* TODO document why this constructor is empty */ }

  ngOnInit(): void {
    if (!this.authService.isLoggedUser()) {
      this.router.navigateByUrl('/login');
    }
    this.documentSentimentService.getSizeAllDocuments().subscribe( (data: any) => {
      let num: number = data;
      this.lengthDataset = num;
      this.documentSentimentService.getAllDocumentSentimentPaged(this.numPage, this.numElementi).subscribe( (lista: any) => {
        lista.forEach( (el: any) => {
          let elemento: DocumentAnalyzed = el;
          this.DATASET.push(el);
        });
      });
    });
  }

  changePageEvent(event: PageEvent) {
    this.lengthDataset = event.length;
    this.numElementi = event.pageSize;
    this.numPage = event.pageIndex;
    this.DATASET = [];
    this.documentSentimentService.getAllDocumentSentimentPaged(this.numPage, this.numElementi).subscribe( (lista: any) => {
      lista.forEach( (el: any) => {
        let elemento: DocumentAnalyzed = el;
        this.DATASET.push(el);
      });
    });
  }

  vaiDettagli(elemento: any): void{
    localStorage.setItem("idElementoAnalizzatoDettagli", elemento.id);
    localStorage.setItem('sizeElementoAnalizzatoDettagli', elemento.numberDocs);
    this.router.navigateByUrl('/home/elenco/dettagli');
  }

  arrotonda(num: number): number{
    return Math.round(num);
  }

  getMese(m: string): string{
    switch (m) {
      case '01': return 'Gennaio' ;
      case '02': return 'Febbraio' ;
      case '03': return 'Marzo' ;
      case '04': return 'Arpile' ;
      case '05': return 'Maggio' ;
      case '06': return 'Giugno' ;
      case '07': return 'Luglio' ;
      case '08': return 'Agosto' ;
      case '09': return 'Settembre' ;
      case '10': return 'Ottobre' ;
      case '11': return 'Novembre' ;
      case '01': return 'Dicembre' ;
      default: return 'ERROR' ;
    }
  }

  func(): string {
    let a = this.DATASET;
    let keys: string[] = [];
    let righe = 0;
    let colonne = 0;
    a.forEach((_el: any) => {
      if (righe == 0) {
        for (let k in _el) {
          keys.push(k);
          colonne++;
        }
      }
      righe++;
    });
    return "( #righe=" + righe + " ; valori_colonne={" + keys.toString() + "} #colonne=" + keys.length + " )";
  }

}
