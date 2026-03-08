import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AuthService, LoginRequest } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  username = '';
  password = '';
  message = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login(): void {

    const req: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.auth.login(req).subscribe({
      next: () => {
        this.router.navigate(['/pokemons']);
      },
      error: () => {
        this.message = 'Login fehlgeschlagen';
      }
    });
  }
}

