import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';

import { UserService } from '../user.service';
import { User } from '../user';
import { AuthService } from '../../auth.service';
import {AsyncPipe} from '@angular/common';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [RouterLink, FormsModule, AsyncPipe],
  templateUrl: './account.html',
})
export class AccountComponent implements OnInit {

  user$!: Observable<User | null>;

  constructor(
    protected auth: AuthService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user$ = this.userService.getUser();
  }

  updateUser(user: User): void {
    this.userService.updateUser(user).subscribe(() => {
      console.log('User updated');
    });
  }

  deleteUser(): void {
    this.userService.deleteUser().subscribe(() => {
      this.auth.logout();
    });
  }
}
