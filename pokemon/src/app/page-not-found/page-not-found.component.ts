import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-page-not-found',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="text-center">
      <img
        src="http://assets.pokemon.com/assets/cms2/img/pokedex/full/035.png"
        alt="Pokémon introuvable"
      />
      <h1>Hey, cette page n'existe pas !</h1>
      <a routerLink="/pokemons" class="waves-effect waves-teal btn-flat">
        Retourner à l'accueil
      </a>
    </div>
  `
})
export class PageNotFoundComponent {}

