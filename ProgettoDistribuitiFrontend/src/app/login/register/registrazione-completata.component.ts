import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/servizi/auth.service';

@Component({
  selector: 'app-registrazione-completata',
  templateUrl: './registrazione-completata.component.html',
  styleUrls: ['./registrazione-completata.component.css']
})
export class RegistrazioneCompletataComponent implements OnInit {

  constructor(private router: Router, private auth: AuthService) { }

  ngOnInit(): void {
    if (this.auth.isLoggedUser()) {
      this.router.navigate(['/home']);
    } else {
      setTimeout(() => {}, 3500);
      this.router.navigateByUrl('/login');
    }
  }

  login(): void{
    this.router.navigate(['/login']);
  }

}
