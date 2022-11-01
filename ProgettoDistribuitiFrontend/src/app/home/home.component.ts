import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Utente } from '../model/utente';
import { AuthService } from '../servizi/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  caricato = false;
  utenteLogged!: Utente;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    if (!this.authService.isLoggedUser()) {
      this.router.navigateByUrl('/login');
    } else{
      this.authService.getUtenteByEmail(localStorage.getItem("emailLogin") + "").subscribe( data => {
        let utente: Utente = data;
        this.utenteLogged = utente;
        this.caricato = true;
      });
    }
  }

}
