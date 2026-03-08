import { Routes } from '@angular/router';
import { ListPokemonComponent } from './list-pokemon/list-pokemon.component';
import { DetailPokemonComponent } from './detail-pokemon/detail-pokemon.component';
import { EditPokemonComponent } from './edit-pokemon/edit-pokemon.component';
import { AddpokemonComponent } from './addpokemon/addpokemon.component';
import { MyPokemonsComponent } from './my-pokemons/my-list-pokemon/my-list-pokemon';
import { MyDetailPokemon } from './my-pokemons/my-detail-pokemon/my-detail-pokemon';
import { authGuard } from '../auth.guard';

export const pokemonRoutes: Routes = [
  {
    path: 'pokemons',
    children: [
      { path: '', component: ListPokemonComponent },
      { path: 'add', component: AddpokemonComponent, canActivate: [authGuard]},
      { path: 'my-pokemons', component: MyPokemonsComponent, canActivate: [authGuard]},
      { path: 'edit/:id', component: EditPokemonComponent, canActivate: [authGuard]},
      { path: ':id', component: DetailPokemonComponent },
      { path: 'my-pokemons/:id', component: MyDetailPokemon, canActivate: [authGuard]}
    ]
  }
];
