import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {baseUrl} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  getUserById(id: bigint) : Observable<any> {
    return this.http.get(baseUrl + "user/" + id)
  }
}
