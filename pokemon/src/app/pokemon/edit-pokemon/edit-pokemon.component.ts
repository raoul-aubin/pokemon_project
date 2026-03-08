import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

import { Pokemon } from '../pokemon';
import { PokemonService } from '../pokemon.service';
import { PokemonFormComponent } from '../pokemon-form/pokemon-form.component';
import {Observable, switchMap} from 'rxjs';

@Component({
  selector: 'app-edit-pokemon',
  standalone: true,
  imports: [CommonModule, PokemonFormComponent],
  template: `
    @if (pokemon$ | async; as pokemon) {
      <h2 class="text-center">Editer {{ pokemon.name }}</h2>

      <p class="text-center">
        <img [src]="pokemon.picture" alt="pokemon">
      </p>

      <app-pokemon-form [pokemon]="pokemon"></app-pokemon-form>
    } @else {
      <h2 class="text-center">Chargement...</h2>
    }
  `
})
export class EditPokemonComponent implements OnInit {

  pokemon$!: Observable<Pokemon | undefined>;

  constructor(
    private route: ActivatedRoute,
    private pokemonService: PokemonService
  ) {}

  ngOnInit(): void {
    this.pokemon$ = this.route.paramMap.pipe(
      switchMap(params =>
        this.pokemonService.getPokemonById(+params.get('id')!)
      )
    );
  }
}

