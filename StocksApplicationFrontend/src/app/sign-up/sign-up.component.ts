import {Component} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {

  form: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';


  constructor(private authService: AuthService, private router: Router) {
  }

  onSubmit(): void {
    this.authService.signUp(this.form).subscribe({
      next: () => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        this.router.navigate(['/sign_in']);
      },
      error: (err) => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    })
  }
}
