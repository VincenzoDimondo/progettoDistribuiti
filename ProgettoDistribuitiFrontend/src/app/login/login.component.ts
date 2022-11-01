import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Utente } from '../model/utente';
import { AuthService } from '../servizi/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required]);
  utente!: Utente;
  hide = true;
  message: string = '';
  @Input() drawer: any;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    if (this.authService.isLoggedUser()) {
      this.router.navigateByUrl('/home');
    }
  }

  vaiRegistrati(): void{
    this.router.navigateByUrl('/login/register');
  }

  login(): void{
    this.authService.login(this.emailFormControl.value + "", this.passwordFormControl.value + "").subscribe( result => {
      this.authService.convalidaLogin(this.emailFormControl.value +"");
      this.router.navigateByUrl('/home');
    }, err => {
      this.message = JSON.stringify(err.error);
      setTimeout(() => { this.message = ''; }, 5000);
    });
  }

}
