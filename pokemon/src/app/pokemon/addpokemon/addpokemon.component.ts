import { Component, OnInit } from '@angular/core';

import { Pokemon } from '../pokemon';
import { PokemonFormComponent } from '../pokemon-form/pokemon-form.component';

@Component({
  selector: 'app-addpokemon',
  standalone: true,
  imports: [PokemonFormComponent],
  template: `
    <h2 class="text-center">Ajouter un pokémon</h2>
    <app-pokemon-form [pokemon]="pokemon"></app-pokemon-form>
  `
})
export class AddpokemonComponent implements OnInit {

  pokemon!: Pokemon;

  ngOnInit(): void {
    this.pokemon = new Pokemon();
  }
}

