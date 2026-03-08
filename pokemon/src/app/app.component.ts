import { Component } from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {AuthService} from './auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(protected auth: AuthService
) {}

  logout(): void {
    this.auth.logout();
  }
}
