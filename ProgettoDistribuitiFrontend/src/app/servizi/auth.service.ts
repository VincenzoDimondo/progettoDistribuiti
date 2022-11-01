import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Utente } from '../model/utente';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private REGISTER_URL = 'http://localhost:8080/utente/create' ;
  private LOGIN_URL = 'http://localhost:8080/utente/login';
  private SEARCH_EMAIL_URL = 'http://localhost:8080/utente/getByEmail' ;
  private UPDATE_USER_URL = 'http://localhost:8080/utente/update' ;
  private REMOVE_USER_URL = 'http://localhost:8080/utente/remove' ;
  private GET_ALL_USER_URL = 'http://localhost:8080/utente/getAll' ;

  private auth = '' ;

  constructor(private http: HttpClient) { }

  register(utente: Utente): Observable<any>{
    return this.http.post(this.REGISTER_URL, utente, { headers: { 'Content-Type': 'application/json' } }) ;
  }

  login(email: string, password: string): Observable<any>{
    return this.http.post(this.LOGIN_URL + '?email=' + email + '&password=' + password,
      { headers: { 'Content-Type': 'application/json' } });
  }

  convalidaLogin(email: string): void {
    localStorage.setItem('emailLogin', email);
  }
  convalidaLogout(): void {
    localStorage.removeItem('emailLogin');
   }
  isLoggedUser(): boolean { return null !== localStorage.getItem('emailLogin'); }

  getUtenteByEmail(email: string): Observable<Utente>{
    return this.http.get<Utente>(this.SEARCH_EMAIL_URL + '?email=' + email,
    { headers: { 'Content-Type': 'application/json' } });
  }

  aggiornaUtente(utente: Utente): Observable<any>{
    return this.http.post( this.UPDATE_USER_URL , utente ,
        { headers : { 'Content-Type' : 'application/json' } });
  }

  cancellaUtente(utente: Utente): Observable<any>{
    return this.http.post( this.REMOVE_USER_URL , utente , { headers : { 'Content-Type' : 'application/json' } } );
  }

  getAllUtenti(): Observable<any>{
    return this.http.get(this.GET_ALL_USER_URL , { headers : { 'Content-Type' : 'application/json' } }) ;
  }

}
