import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Utente } from 'src/app/model/utente';
import { AuthService } from 'src/app/servizi/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  message = '' ;
  nomeFormControl = new FormControl('', [Validators.required, Validators.minLength(3) ]);
  cognomeFormControl = new FormControl('', [Validators.required, Validators.minLength(3) ]);
  emailFormControl = new FormControl('', [Validators.required, Validators.email ]);
  passwordFormControl = new FormControl('', [Validators.required ]);
  hide = true;

  constructor(fb: FormBuilder, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    if (this.authService.isLoggedUser()) {
      this.router.navigate(['/home']);
    }
  }

  addUser(): void{
    let utente = new Utente();
    utente.nome = "" + this.nomeFormControl.value;
    utente.cognome = "" + this.cognomeFormControl.value;
    utente.email = "" + this.emailFormControl.value;
    utente.password = "" + this.passwordFormControl.value;
    this.authService.register(utente).subscribe( res => {
      if (JSON.stringify(res) === '200'){
        this.router.navigate(['/register/complete']);
      }
    },  err => {
        if(JSON.stringify(err.error.text) === 'Errore interno al Server!'){
            this.router.navigate(['register/failed']);
        } else {
            this.message = JSON.stringify(err.error.text) ;
            setTimeout(() => { this.message = ''; }, 5000);
        }
    });
  }

}
