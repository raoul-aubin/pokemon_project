import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { pokemonRoutes } from './pokemon/pokemon.routes';
import {RegisterComponent} from './register/register';
import {AccountComponent} from './user/account/account';
import {ChangePasswordComponent} from './user/change-password/change-password';
import { authGuard } from './auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'pokemons', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component : RegisterComponent },
  { path: 'account', component : AccountComponent, canActivate: [authGuard]},
  { path: 'change-password', component : ChangePasswordComponent, canActivate: [authGuard]},
  ...pokemonRoutes,
  { path: '**', component: PageNotFoundComponent }
];
