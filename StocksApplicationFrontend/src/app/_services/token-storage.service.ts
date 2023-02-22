import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {Router} from "@angular/router";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private _isLoggedIn$ = new BehaviorSubject<boolean>(false);
  isLoggedIn$ = this._isLoggedIn$.asObservable();

  constructor(private router: Router) {
    this._isLoggedIn$.next(!!this.getToken())
  }

  signOut(): void {
    window.localStorage.clear();
    this._isLoggedIn$.next(false);
    this.router.navigate(['/sign_in']);
  }

  public saveToken(token: string): void {
    this._isLoggedIn$.next(true);
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    // @ts-ignore
    return localStorage.getItem(TOKEN_KEY);
  }

  public saveUser(user: any): void {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    // @ts-ignore
    return JSON.parse(localStorage.getItem(USER_KEY));
  }

  public isUserLoggedIn() : boolean {
    return !!this.getToken();
  }
}
