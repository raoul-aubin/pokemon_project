import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router} from '@angular/router';

import { debounceTime, distinctUntilChanged, map, Observable, Subject, switchMap } from 'rxjs';

import { Pokemon } from '../pokemon';
import { PokemonService } from '../pokemon.service';

@Component({
  selector: 'app-search-pokemon',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './search-pokemon.component.html'
})
export class SearchPokemonComponent implements OnInit {

  private readonly searchTerms = new Subject<string>();
  pokemons$!: Observable<Pokemon[]>;

  constructor(
    private router: Router,
    private pokemonService: PokemonService
  ) {}

  ngOnInit(): void {
    this.pokemons$ = this.searchTerms.pipe(
      map(t => t.trim()),
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => this.pokemonService.searchPokemonList(term))
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  goToDetail(pokemon: Pokemon): void {
    this.router.navigate(['/pokemons', pokemon.id]);
  }
}

