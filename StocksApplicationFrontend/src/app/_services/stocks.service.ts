import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {baseUrl, httpOptions} from "../../environments/environment";
import {Observable} from "rxjs";
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class StocksService {

  constructor(private http: HttpClient) { }

  getStocks(): Observable<any> {
    return this.http.get(baseUrl + 'stock');
  }

  executeStockOperation(request: FormGroup, operation: string): Observable<any> {
    return this.http.put(baseUrl + 'stock', {
      amountOfStocks: request.value.amountOfStocks,
      stockId: request.value.stockId,
      operation: operation
    }, httpOptions);
  }
}
