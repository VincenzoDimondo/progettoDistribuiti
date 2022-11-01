import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/servizi/auth.service';

@Component({
  selector: 'app-registrazione-fallita',
  templateUrl: './registrazione-fallita.component.html',
  styleUrls: ['./registrazione-fallita.component.css']
})
export class RegistrazioneFallitaComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    if (this.auth.isLoggedUser()) {
      this.router.navigate(['/home']);
    }
    else {
      setTimeout(() => { }, 2700);
      this.router.navigateByUrl('/login');
    }
  }

}
