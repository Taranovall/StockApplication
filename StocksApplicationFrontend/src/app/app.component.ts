import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "./_services/token-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Stocks application';

  constructor(public tokenService: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.isTokenExpired(this.tokenService.getToken())) {
      this.tokenService.signOut();
    }
  }

  private isTokenExpired(token: string) {
    if (!this.tokenService.getToken()) return;

    const tokenPayload = atob(token?.split('.')[1]);
    const expirationDate = JSON.parse(tokenPayload).exp * 1000;

    return expirationDate < Date.now();
  }
}
