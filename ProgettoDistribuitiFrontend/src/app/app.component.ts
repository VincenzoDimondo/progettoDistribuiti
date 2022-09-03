import {Component, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import { AuthService } from './servizi/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  title = 'ProgettoDistribuiti';
  loggedIn = false;
  @Output() drawer: any;

  constructor(private router: Router, private authService: AuthService) {
  }
  ngOnInit(): void {
    if(this.authService.isLoggedUser()){
      this.loggedIn = true;
    }
  }

  vaiTraccia(): void {
    this.router.navigate(['home/traccia']);
  }

  vaiHome(): void {
    this.router.navigate(['/']);
  }

  vaiElenco(): void {
    this.router.navigate(['home/elenco']);
  }

  vaiInserisci(): void {
    this.router.navigate(['home/inserisci']);
  }

  vaiLogin(): void{
    this.router.navigateByUrl('/login');
  }

  vaiLogout(): void {
    this.authService.convalidaLogout();
    this.router.navigateByUrl('/login');
  }

}
