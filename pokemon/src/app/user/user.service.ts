import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { User } from './user';
import { HttpClient } from '@angular/common/http';

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private api = 'http://localhost:8080/pokemon_backend/api/me';

  constructor(private http: HttpClient) {}

  getUser(): Observable<User | null> {
    return this.http.get<User>(`${this.api}`).pipe(
      tap(response => this.log(response)),
      catchError(error => this.handleError(error, null))
    );
  }

  updateUser(user: User): Observable<void> {
    return this.http.put<void>(`${this.api}`, user).pipe(
      catchError(error => this.handleError(error, undefined))
    );
  }

  deleteUser(): Observable<void> {
    return this.http.delete<void>(`${this.api}`).pipe(
      catchError(error => this.handleError(error, undefined))
    );
  }

  changePassword(req: ChangePasswordRequest): Observable<void> {
    return this.http.put<void>(`${this.api}/password`, req).pipe(
      catchError(error => this.handleError(error, undefined))
    );
  }

  private log(response: unknown): void {
    console.table(response);
  }

  private handleError<T>(error: unknown, result: T): Observable<T> {
    console.error(error);
    return of(result);
  }
}
