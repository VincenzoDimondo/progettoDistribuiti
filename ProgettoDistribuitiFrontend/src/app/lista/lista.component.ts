import { Component, Input, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { DocumentAnalyzed } from '../model/document-analyzed';
import { AuthService } from '../servizi/auth.service';
import { DocumentSentimentService } from '../servizi/document-sentiment.service';

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css'],
})
export class ListaComponent implements OnInit {

  id: number = -1;
  numPage: number = 0;
  numElementi: number = 15;
  numDocs!: number;
  load: boolean = false;
  dataSource: any;
  displayedColumns: string[] = ['sentence text', 'sentiment', '% confidence score positive', '% confidence score neutre', '% confidence score negative'];
  items = Array.from({ length: 0 }).map((_, i) => `Item #${i}`);
  //confidenceScores: any;

  constructor(private documentSentimentService: DocumentSentimentService, private router: Router, private authService: AuthService) {
    /* TODO document why this constructor is empty */
  }

  ngOnInit(): void {
    if (!this.authService.isLoggedUser()) {
      this.router.navigateByUrl('/login');
    }
    if( this.id == NaN ){ this.router.navigateByUrl('/home/elenco'); }
    if( this.id != -1 ){ return; }
    this.id = Number.parseInt('' + localStorage.getItem('idElementoAnalizzatoDettagli'));
    this.numDocs = Number.parseInt('' + localStorage.getItem('sizeElementoAnalizzatoDettagli'));
    localStorage.removeItem('idElementoAnalizzatoDettagli');
    this.documentSentimentService.getDocumentByIdPageable(this.id, this.numPage, this.numElementi).subscribe( (data: any) => {
      //this.documentSentimentService.getDocumentSentiment(num).subscribe( (el: any) => {
      //let documento: DocumentAnalyzed = el;
      //this.confidenceScores = documento.confidenceScores;
      this.dataSource = data;
      this.items = Array.from({ length: data.length }).map((_, i) => `Item #${i}`);
      //console.log(this.dataSource[1]);
    }, err => {
      this.router.navigateByUrl('/home/elenco');
    });
    this.load=true;
  }

  changePageEvent(event: PageEvent) {
    this.load = false;
    this.numDocs = event.length;
    this.numElementi = event.pageSize;
    this.numPage = event.pageIndex;
    this.documentSentimentService.getDocumentByIdPageable(this.id, this.numPage, this.numElementi).subscribe( (data: any) => {
      //this.documentSentimentService.getDocumentSentiment(num).subscribe( (el: any) => {
      //let documento: DocumentAnalyzed = el;
      //this.confidenceScores = documento.confidenceScores;
      this.dataSource = data;
      this.items = Array.from({ length: data.length }).map((_, i) => `Item #${i}`);
      //console.log(this.dataSource[1]);
    }, err => {
      console.log(err);
      //this.router.navigateByUrl('/home/elenco');
    });
    this.load = true;
  }

  arrotonda(num: number): number {
    return Math.round(num);
  }

  vaiElenco(): void{
    this.router.navigateByUrl('/home/elenco');
  }

  vaiElimina(): void{
    this.documentSentimentService.removeDocumentSentiment(this.id).subscribe( () => {
      this.router.navigateByUrl('/home/elenco');
    });
  }

}
