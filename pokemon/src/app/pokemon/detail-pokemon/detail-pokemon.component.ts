import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import {Observable, switchMap} from 'rxjs';

import { Pokemon } from '../pokemon';
import { PokemonService } from '../pokemon.service';
import {LoaderComponent} from '../loader/loader.component';
import { PokemonTypeColorPipe } from '../pokemon-type-color.pipe';

@Component({
  selector: 'app-detail-pokemon',
  standalone: true,
  imports: [
    CommonModule,
    PokemonTypeColorPipe,
    LoaderComponent
  ],
  templateUrl: './detail-pokemon.component.html'
})
export class DetailPokemonComponent implements OnInit {

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



