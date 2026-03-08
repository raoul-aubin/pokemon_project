import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AuthService, RegisterRequest } from '../auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html'
})
export class RegisterComponent {

  username = '';
  email = '';
  password = '';
  message = '';

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  register(): void {
    const req: RegisterRequest = {
      username: this.username,
      email: this.email,
      password: this.password
    };

    this.auth.register(req).subscribe({
      next: () => {
        this.router.navigate(['/pokemons']);
      },
      error: () => {
        this.message = 'Registrierung fehlgeschlagen';
      }
    });
  }
}
