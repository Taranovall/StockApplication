import {HttpHeaders} from "@angular/common/http";

export const baseUrl  = 'http://localhost:8080/';

export const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};
