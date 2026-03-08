import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of, tap } from 'rxjs';

import { Pokemon } from './pokemon';

@Injectable({
  providedIn: 'root'
})
export class PokemonService {

  constructor(private http: HttpClient) {}

  getPokemonList(): Observable<Pokemon[]> {
    return this.http.get<Pokemon[]>('http://localhost:8080/pokemon_backend/api/pokemons').pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, []))
    );
  }

  getPokemonById(pokemonId: number): Observable<Pokemon | undefined> {
    return this.http.get<Pokemon>(`http://localhost:8080/pokemon_backend/api/pokemons/${pokemonId}`).pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, undefined))
    );
  }

  getPokemonByOwner(): Observable<Pokemon[]> {
    return this.http.get<Pokemon[]>('http://localhost:8080/pokemon_backend/api/me/pokemons').pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, []))
    );
  }

  searchPokemonList(term: string): Observable<Pokemon[]> {
    if (term.length <= 1) {
      return of([]);
    }

    return this.http.get<Pokemon[]>(`http://localhost:8080/pokemon_backend/api/pokemons/search?name=${term}`).pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, []))
    );
  }

  addPokemon(pokemon: Pokemon): Observable<Pokemon> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    return this.http.post<Pokemon>('http://localhost:8080/pokemon_backend/api/pokemons', pokemon, httpOptions).pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, pokemon))
    );
  }

  updatePokemon(pokemon: Pokemon): Observable<null> {
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    return this.http.put<null>(`http://localhost:8080/pokemon_backend/api/pokemons/${pokemon.id}`, pokemon, httpOptions).pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, null))
    );
  }

  deletePokemonById(pokemonId: number): Observable<null> {
    return this.http.delete<null>(`http://localhost:8080/pokemon_backend/api/pokemons/${pokemonId}`).pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, null))
    );
  }

  getPokemonTypeList(): string[] {
    return [
      'Plante',
      'Feu',
      'Eau',
      'Insecte',
      'Normal',
      'Vol',
      'Poison',
      'Fée',
      'Psy',
      'Electrik',
      'Combat'
    ];
  }

  private log(response: unknown): void {
    console.table(response);
  }

  private handleError<T>(error: unknown, result: T): Observable<T> {
    console.error(error);
    return of(result);
  }
}

