import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { UserService, ChangePasswordRequest } from '../user.service';
import {AuthService} from '../../auth.service';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [
    RouterLink,
    FormsModule
  ],
  templateUrl: './change-password.html',
})
export class ChangePasswordComponent {

  currentPassword = '';
  newPassword = '';

  constructor(
    protected auth: AuthService,
    private userService: UserService,
  ) {}

  changePassword(): void {

    const req: ChangePasswordRequest = {
      currentPassword: this.currentPassword,
      newPassword: this.newPassword
    };

    this.userService.changePassword(req).subscribe(() => {
      this.auth.logout();
    });

  }

}
