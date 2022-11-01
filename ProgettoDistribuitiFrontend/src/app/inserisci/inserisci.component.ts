import { Component, OnInit, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TextAnalyticsClient, AzureKeyCredential, AnalyzeSentimentResult, AnalyzeSentimentResultArray, TextDocumentInput } from '@azure/ai-text-analytics';
import { FileInput } from 'ngx-material-file-input';
import { AuthService } from '../servizi/auth.service';
import { DocumentSentimentService } from '../servizi/document-sentiment.service';

@Component({
  selector: 'app-inserisci',
  templateUrl: './inserisci.component.html',
  styleUrls: ['./inserisci.component.css'],
})
export class InserisciComponent implements OnInit {
  fileCaricato = -1;
  percentuale = -1;
  caricato = false;
  verificaUrl = false;
  filesTot: any ;
  linguaFormControl = new FormControl('', [Validators.required]);
  urlFormControl = new FormControl('', [Validators.required]);
  fileControl = new FormControl('', [Validators.required]);;
  fileForm: FormGroup = new FormGroup({ fileControl : this.fileControl  });
  enterControl = new FormControl('');
  enterStyle?: string;
  private endpoint = 'https://servizicognitivi.cognitiveservices.azure.com/';
  private url_azure = '/language/:analyze-text?api-version=2022-05-01';
  private key1 = '3b28950d551a4a18892aea93393bd9eb';
  private textAnalyticsClient: TextAnalyticsClient = new TextAnalyticsClient(
    this.endpoint,
    new AzureKeyCredential(this.key1)
  );
  private sentimentResult: any;
  private sentimentOuput: any[] = [];
  txt: string = '';
  urlInserito: any;
  sentimentInput: string[] = [];

  constructor(private documentSentimentService: DocumentSentimentService, private authService: AuthService, private router: Router) {
    /* TODO document why this constructor is empty */
  }

  ngOnInit(): void {
    if (!this.authService.isLoggedUser()) {
      this.router.navigateByUrl('/login');
    }
    this.fileControl.valueChanges.subscribe((files: any) => {
      if (!Array.isArray(files)) {
        this.percentuale = 10;
        this.sentimentInput = [];
        files.text().then((data: any) => {
          this.txt = data;
          let str: string = '';
          //          let str_tot: string = "";
          let index: number;
          //console.log('txt = ' + data);
          //console.log("length_text = " + this.txt.length);
          for (index = 0; index < this.txt.length; index++) {
            let n = this.txt[index];
            if (n == '\n' && this.txt[index - 1] == '}') {
              let el = JSON.parse(str);
              //              str_tot = str_tot + el.full_text + '\n';
              //console.log("full_text = " + el.full_text +"\n");
              this.sentimentInput.push(el.full_text);
              str = '';
            } else {
              str = str + '' + n;
            }
            this.percentuale += 90 / this.txt.length;
          }
          //          this.sentimentInput.push(str_tot);
          console.log('length_list_input = ' + this.sentimentInput.length);
          //console.log('--->\n' + JSON.stringify(this.sentimentInput));
          this.percentuale = -1;
          // the handler, e.g. if user presses "upload"-button
          const file_form = this.fileControl.get('fileForm');
          //const file = file_form.files[0]; // in case user didn't selected multiple files
          console.log("file_  " + file_form);
          const formData = new FormData();
          //formData.append('file', file); // attach blob to formdata / preparing the request
        }, (err: any) => {
          console.log(" E R R O R   !!! ");
        });
      }
    });
  }

  riprova(target: any | undefined): void {
    let stringa_di_testo = target.files[0];
    this.percentuale = 0;
    this.sentimentInput = [];
    this.txt = stringa_di_testo;
    let str: string = '';
    //          let str_tot: string = "";
    let index: number;
    console.log('txt = ' + JSON.stringify(this.txt));
    //console.log('length = ' + this.txt[2]);
    for (index = 0; index < this.txt.length; index++) {
      let n = this.txt[index];
      if (n == '\n' && this.txt[index - 1] == '}') {
        let el = JSON.parse(str);
        //              str_tot = str_tot + el.full_text + '\n';
        //console.log("full_text = " + el.full_text +"\n");
        this.sentimentInput.push(el.full_text);
        str = '';
      } else {
        str = str + '' + n;
      }
      this.percentuale += 100 / this.txt.length;
    }
    //          this.sentimentInput.push(str_tot);
    //console.log('--->\n' + JSON.stringify(this.sentimentInput));
    this.percentuale = -1;
  }

  valuta(): void {
    //http://time.jsontest.com
    //https://jsonplaceholder.typicode.com/users
    fetch('' + this.urlFormControl.value)
      .then((res) => res.json())
      .then((out) => {
        console.log('Checkout this JSON! ', out);
      })
      .catch((err) => {
        console.log('error = ' + err);
      });
    this.caricato = true;
  }

  stampaFile(): void {
    //console.log('valueChanges = ' + this.fileControl.select);
  }

  resetAll(): boolean {
    this.enterControl.setValue('');
    this.percentuale = -1;
    return true;
  }

  load(file: any): void {
    let obj = file.files.item(0);
    let oggettoJson = obj.text().then((data: any) => {
      console.log(data);
    });
    console.log(obj);
    console.log(JSON.stringify(obj));
    console.log(oggettoJson);
    console.log(JSON.stringify(oggettoJson));
  }

  sentimentAnalysisProcedure(): void {
    this.percentuale = 10;
    let input: string[] = [];
    let j: number;
    for (j = 0; j < Math.floor(this.sentimentInput.length / 10); j++) {
      input = [];
      input.push(this.sentimentInput[j * 10 + 0]);
      input.push(this.sentimentInput[j * 10 + 1]);
      input.push(this.sentimentInput[j * 10 + 2]);
      input.push(this.sentimentInput[j * 10 + 3]);
      input.push(this.sentimentInput[j * 10 + 4]);
      input.push(this.sentimentInput[j * 10 + 5]);
      input.push(this.sentimentInput[j * 10 + 6]);
      input.push(this.sentimentInput[j * 10 + 7]);
      input.push(this.sentimentInput[j * 10 + 8]);
      input.push(this.sentimentInput[j * 10 + 9]);
      this.sentimentAnalysis(input);
    }
    if (this.sentimentInput.length % 10 != 0) {
      input = [];
      let jk = 0;
      while (jk != this.sentimentInput.length % 10) {
        input.push(this.sentimentInput[j * 10 + jk]);
        jk++;
      }
      this.sentimentAnalysis(input);
    }
  }

  async sentimentAnalysis(input: string[]): Promise<void> {
    //console.log('--->\n' + JSON.stringify(this.sentimentInput));
    try {
      this.sentimentResult = await this.textAnalyticsClient.analyzeSentiment(input);
    } catch (error) {

    }
    this.percentuale += ((input.length * 90) / this.sentimentInput.length) ;
    this.sentimentResult.forEach((document: any) => {
      this.sentimentOuput.push(document);
      /*
      console.log('All = \n' + JSON.stringify(document));
      console.log(`ID: ${document.id}`);
      console.log(`\tDocument Sentiment: ${document.sentiment}`);
      console.log(`\tDocument Scores:`);
      console.log(`\t\tPositive: ${document.confidenceScores.positive.toFixed(2)} \tNegative: ${document.confidenceScores.negative.toFixed(2)} \tNeutral: ${document.confidenceScores.neutral.toFixed(2)}`);
      console.log(`\tSentences Sentiment(${document.sentences.length}):`);
      document.sentences.forEach((sentence: any) => {
        console.log(`\t\tSentence sentiment: ${sentence.sentiment}`);
        console.log(`\t\tSentences Scores:`);
        console.log(`\t\tPositive: ${sentence.confidenceScores.positive.toFixed(2)} \tNegative: ${sentence.confidenceScores.negative.toFixed(2)} \tNeutral: ${sentence.confidenceScores.neutral.toFixed(2)}`);
      });
      */
    });
    this.percentuale = ((Math.round(this.percentuale*100)/100));
    if (this.percentuale == 100) {
      console.log("dentro = " + this.percentuale)
      //chiamata REST backend
      this.documentSentimentService.createDocumentSentiment(this.sentimentOuput).subscribe(() => {

      }, err => {

        console.log("Error Database!");

      });
      this.sentimentOuput = [];
    }
  }

  provaValuta(): void {
    const All: AnalyzeSentimentResult = {
      id: '6',
      warnings: [],
      sentiment: 'negative',
      confidenceScores: { positive: 0.02, neutral: 0.13, negative: 0.85 },
      sentences: [
        {
          confidenceScores: { positive: 0.01, neutral: 0.06, negative: 0.93 },
          sentiment: 'negative',
          text: '@peterdaou Not true. ',
          offset: 0,
          length: 21,
          opinions: [],
        },
        {
          confidenceScores: { positive: 0.04, neutral: 0.35, negative: 0.61 },
          sentiment: 'negative',
          text: 'Trump is here despite most GOP having strongly opposed him. ',
          offset: 21,
          length: 60,
          opinions: [],
        },
        {
          confidenceScores: { positive: 0, neutral: 0, negative: 0.99 },
          sentiment: 'negative',
          text: 'He won by courting ppl who felt invisible to GOP, AND becos a lot of ppl who hated voting 4 Trump, did so becos they considered Hilary a greater evil. ',
          offset: 81,
          length: 151,
          opinions: [],
        },
        {
          confidenceScores: { positive: 0.03, neutral: 0.11, negative: 0.86 },
          sentiment: 'negative',
          text: 'Most repubs r not extremists or racists, but some r elit... ',
          offset: 232,
          length: 60,
          opinions: [],
        },
        {
          confidenceScores: { positive: 0.08, neutral: 0.89, negative: 0.03 },
          sentiment: 'neutral',
          text: 'https://t.co/T9yumBmhLY',
          offset: 292,
          length: 23,
          opinions: [],
        },
      ],
    };
    this.documentSentimentService
      .createDocumentSentiment(All)
      .subscribe(() => {});
  }
}
