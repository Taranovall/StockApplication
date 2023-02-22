import {Component, OnInit} from '@angular/core';
import {StocksService} from "../_services/stocks.service";
import {MatTableDataSource} from "@angular/material/table";
import {TokenStorageService} from "../_services/token-storage.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface Stock {
  symbol: string;
  name: string;
  price: number;
}

@Component({
  selector: 'app-stocks',
  templateUrl: './stocks.component.html',
  styleUrls: ['./stocks.component.css']
})
export class StocksComponent implements OnInit {
  displayedColumns: string[] = ['symbol', 'name', 'price', 'buy'];
  stocks = new MatTableDataSource<Stock[]>();
  form: any = {};
  message: string = '';


  constructor(private stockService: StocksService, private tokenService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      amountOfStocks: new FormControl('', [Validators.required, Validators.min(1)]),
      stockId: new FormControl('', [Validators.pattern("^(\\d+)$"), Validators.required])
    });

    this.stockService.getStocks().subscribe({
        next: (data) => {
          this.stocks.data = data;
        },
        error: (err) => {
          this.stocks = JSON.parse(err.error).message;
        }
      }
    )
  };

  onSubmit(stockId: number) {
    this.form.controls.stockId.setValue(stockId);
    this.stockService.executeStockOperation(this.form, 'buy').subscribe({
      next: (res) => {
        let user = this.tokenService.getUser();
        user.account.amountOfMoney = res.amountOfMoney;
        this.tokenService.saveUser(user);
        this.message = res.message;
        this.form.reset();
        // @ts-ignore
        document.getElementById("money").textContent = res.amountOfMoney.toFixed(2) + " $";
      },
      error: (err) => {
        console.log(err)
        this.message = err.error.message;
      }
    })
  }
}
