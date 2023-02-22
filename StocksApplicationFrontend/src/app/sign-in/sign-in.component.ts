import {Component, Injectable} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {Router} from "@angular/router";
import {TokenStorageService} from "../_services/token-storage.service";

@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string = '';

  constructor(private signInService: AuthService, private tokenStorage: TokenStorageService, private router: Router) {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().role;
    }
  }

  onSubmit(): void {
    this.signInService.signIn(this.form).subscribe({
      next: (res) => {
        debugger
        this.tokenStorage.saveToken(res.token);
        this.tokenStorage.saveUser(res.user);
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.tokenStorage.getUser().role;
        this.router.navigate(['/profile']);
      },
      error: (err) => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    })
  }
}
