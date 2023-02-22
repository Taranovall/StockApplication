import {baseUrl, httpOptions} from "../../environments/environment";
import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  signIn(credentials: { username: string; password: string; }): Observable<any> {
    return this.http.post(baseUrl + 'sign_in', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  signUp(user: any): Observable<any> {
    return this.http.post(baseUrl + 'sign_up', {
      username: user.username,
      password: user.password
    }, httpOptions)
  }
}
