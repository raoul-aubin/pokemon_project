import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { Pokemon } from '../pokemon';
import { PokemonService } from '../pokemon.service';
import {LoaderComponent} from '../loader/loader.component';
import { PokemonTypeColorPipe } from '../pokemon-type-color.pipe';
@Component({
  selector: 'app-pokemon-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    LoaderComponent,
    PokemonTypeColorPipe
  ],
  templateUrl: './pokemon-form.component.html',
  styleUrls: ['./pokemon-form.component.css']
})
export class PokemonFormComponent implements OnInit {

  @Input() pokemon!: Pokemon;

  types: string[] = [];
  isAddForm = false;

  constructor(
    private pokemonService: PokemonService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.types = this.pokemonService.getPokemonTypeList();
    this.isAddForm = this.router.url.includes('add');
  }

  hasType(type: string): boolean {
    return this.pokemon.types.includes(type);
  }

  selectType(event: Event, type: string): void {
    const isChecked = (event.target as HTMLInputElement).checked;

    if (isChecked) {
      this.pokemon.types.push(type);
    } else {
      const index = this.pokemon.types.indexOf(type);
      this.pokemon.types.splice(index, 1);
    }
  }

  isTypesValid(type: string): boolean {
    if (this.pokemon.types.length === 1 && this.hasType(type)) {
      return false;
    }

    if (this.pokemon.types.length > 2 && !this.hasType(type)) {
      return false;
    }

    return true;
  }

  onSubmit(): void {
    if (this.isAddForm) {
      this.pokemonService.addPokemon(this.pokemon)
        .subscribe(pokemon =>
          this.router.navigate(['/pokemons/my-pokemons', pokemon.id])
        );
    } else {
      this.pokemonService.updatePokemon(this.pokemon)
        .subscribe(() =>
          this.router.navigate(['/pokemons/my-pokemons', this.pokemon.id])
        );
    }
  }
}

