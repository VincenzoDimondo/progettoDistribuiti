import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSliderModule} from '@angular/material/slider';
import {MatIconModule} from '@angular/material/icon';
import {RouterModule, Routes} from '@angular/router';
import {MatCardModule} from '@angular/material/card';
import {HttpClientModule} from '@angular/common/http';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatDividerModule} from '@angular/material/divider';
import {MatSortModule} from '@angular/material/sort';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatTableModule} from '@angular/material/table';
import {MatTreeModule} from '@angular/material/tree';
import {NgxMatFileInputModule} from '@angular-material-components/file-input';
import {MatButtonToggleModule} from '@angular/material/button-toggle' ;
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatExpansionModule} from "@angular/material/expansion";
import {ScrollingModule} from '@angular/cdk/scrolling';
import {MatPaginatorModule} from "@angular/material/paginator";


import {AppComponent} from './app.component';
import {BarraComponent} from './barra/barra.component';
import {OrologioComponent} from './barra/clock/orologio.component';
import {TracciaComponent} from './traccia/traccia.component';
import {ElencoComponent} from './elenco/elenco.component';
import {InserisciComponent} from './inserisci/inserisci.component';
import {HomeComponent} from './home/home.component';
import { ListaComponent } from './lista/lista.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './login/register/register.component'
import { RegistrazioneCompletataComponent } from './login/register/registrazione-completata.component';
import { RegistrazioneFallitaComponent } from './login/register/registrazione-fallita.component';

const appRoutes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: 'login', component: LoginComponent },
  { path: 'login/register', component: RegisterComponent },
  { path: 'login/register/complete', component: RegistrazioneCompletataComponent },
  { path: 'login/register/failed', component: RegistrazioneFallitaComponent },
  { path: 'home', component: HomeComponent },
  { path: 'home/traccia', component: TracciaComponent },
  { path: 'home/inserisci', component: InserisciComponent },
  { path: 'home/elenco', component: ElencoComponent },
  { path: 'home/elenco/dettagli', component: ListaComponent },
  { path: '**', pathMatch: 'full', redirectTo: '/login' },
];


@NgModule({
  declarations: [
    AppComponent,
    BarraComponent,
    OrologioComponent,
    TracciaComponent,
    ElencoComponent,
    InserisciComponent,
    HomeComponent,
    ListaComponent,
    LoginComponent,
    RegisterComponent,
    RegistrazioneCompletataComponent,
    RegistrazioneFallitaComponent,

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule,
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    MatSliderModule,
    MatMenuModule,
    MatButtonModule,
    MatSidenavModule,
    MatDividerModule,
    MatSortModule,
    MatDialogModule,
    MatFormFieldModule, FormsModule, ReactiveFormsModule,
    MatSelectModule,
    MatInputModule,
    MatTableModule,
    MatTreeModule,
    NgxMatFileInputModule,
    MatButtonToggleModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    ScrollingModule,
    MatPaginatorModule,
    MatFormFieldModule,


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
