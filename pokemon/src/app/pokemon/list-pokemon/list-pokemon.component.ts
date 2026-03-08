import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';

import { Pokemon } from '../pokemon';
import { PokemonService } from '../pokemon.service';

import { SearchPokemonComponent } from '../search-pokemon/search-pokemon.component';
import { BorderCardDirective } from '../border-card.directive';
import { PokemonTypeColorPipe } from '../pokemon-type-color.pipe';

@Component({
  selector: 'app-list-pokemon',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    SearchPokemonComponent,
    BorderCardDirective,
    PokemonTypeColorPipe
  ],
  templateUrl: './list-pokemon.component.html'
})
export class ListPokemonComponent implements OnInit {

  pokemonList$!: Observable<Pokemon[]>;

  constructor(
    private router: Router,
    private pokemonService: PokemonService
  ) {}

  ngOnInit(): void {
    this.pokemonList$ = this.pokemonService.getPokemonList();
  }

  goToPokemon(pokemon: Pokemon): void {
    this.router.navigate(['/pokemons', pokemon.id]);
  }
}

