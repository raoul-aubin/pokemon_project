import {Component, OnInit} from '@angular/core';
import {Observable, switchMap} from 'rxjs';
import {Pokemon} from '../../pokemon';
import {ActivatedRoute, Router} from '@angular/router';
import {PokemonService} from '../../pokemon.service';
import {CommonModule} from '@angular/common';
import {PokemonTypeColorPipe} from '../../pokemon-type-color.pipe';
import {LoaderComponent} from '../../loader/loader.component';

@Component({
  selector: 'app-my-detail-pokemon',
  standalone: true,
  imports: [
    CommonModule,
    PokemonTypeColorPipe,
    LoaderComponent
  ],
  templateUrl: './my-detail-pokemon.html',
  styles: ``,
})
export class MyDetailPokemon implements OnInit {
  pokemon$!: Observable<Pokemon | undefined>;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pokemonService: PokemonService
  ) {}

  ngOnInit(): void {
    this.pokemon$ = this.route.paramMap.pipe(
      switchMap(params =>
        this.pokemonService.getPokemonById(+params.get('id')!)
      )
    );
  }

  deletePokemon(pokemon: Pokemon): void {
    this.pokemonService.deletePokemonById(pokemon.id!)
      .subscribe(()=>this.router.navigate(['/pokemons/my-pokemons']));
  }

  goToEditPokemon(pokemon: Pokemon): void {
    this.router.navigate(['/pokemons/edit', pokemon.id]);
  }
}
