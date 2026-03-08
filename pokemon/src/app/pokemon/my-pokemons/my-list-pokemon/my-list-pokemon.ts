import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';

import { Pokemon } from '../../pokemon';
import { PokemonService } from '../../pokemon.service';

import { BorderCardDirective } from '../../border-card.directive';
import { PokemonTypeColorPipe } from '../../pokemon-type-color.pipe';

@Component({
  selector: 'app-my-pokemons',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    RouterLink,
    BorderCardDirective,
    PokemonTypeColorPipe,
  ],
  templateUrl: './my-list-pokemon.html',
  styles: ``,
})
export class MyPokemonsComponent implements OnInit{
  pokemonList$!: Observable<Pokemon[]>;

  constructor(
    private router: Router,
    private pokemonService: PokemonService
  ) {}

  ngOnInit(): void {
    this.pokemonList$ = this.pokemonService.getPokemonByOwner();
  }

  goToPokemon(pokemon: Pokemon): void {
    this.router.navigate(['pokemons/my-pokemons', pokemon.id]);
  }
}
