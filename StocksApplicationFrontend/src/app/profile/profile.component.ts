import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "../_services/token-storage.service";
import {UserService} from "../_services/user.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {StocksService} from "../_services/stocks.service";


interface Stock {
  id: number;
  symbol: string;
  name: string;
  price: number;
  amount: number;
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  stocks: Stock[] = [];
  form = new FormGroup({
    amountOfStocks: new FormControl(''),
    stockId: new FormControl(
      Validators.pattern('^(\\d+)$'),
      Validators.required
    ),
  });
  totalPrice = 0;
  message = '';

  constructor(private tokenService: TokenStorageService,
              private userService: UserService,
              private stockService: StocksService) {
  }

  ngOnInit(): void {
    const userId = this.tokenService.getUser().id;
    this.userService.getUserById(userId).subscribe({
      next: (res) => {
        this.currentUser = res;
        this.tokenService.saveUser(res);
        this.convertMapToStockArray(res.account.stocks);
      }
    });
  }

  onSubmit(id: any): void {
    const amountBought = this.form.value.amountOfStocks;
    this.form.controls.stockId.setValue(id);
    this.stockService.executeStockOperation(this.form, 'sell').subscribe({
      next: (res) => {
        const user = this.tokenService.getUser();
        user.account.amountOfMoney = res.amountOfMoney;
        this.tokenService.saveUser(user);
        this.currentUser = user;
        // @ts-ignore
        document.getElementById("money").textContent = `${res.amountOfMoney.toFixed(2)} $`;
        this.message = res.message;
        this.form.reset();

        const index = this.stocks.findIndex(stock => stock.id === Number(id));
        if (index !== -1) {
          // @ts-ignore
          this.stocks[index].amount -= amountBought;
          if (this.stocks[index].amount == 0) {
            this.stocks.splice(index, 1);
          }
        }
        this.totalPrice = this.stocks.reduce((total, stock) => total + stock.price * stock.amount, 0);
      },
      error: (err) => {
        console.log(err)
        this.message = err.error.message;
      }
    });
  }


  private convertMapToStockArray(stocks: any) {
    for (const [key, value] of Object.entries(stocks || {})) {
      const id = Number(key.match(/id=(\d+)/)![1]);
      const symbol = key.match(/symbol=([A-Z]+)/)![1];
      const name = key.match(/name=([a-zA-Z]+)/)![1];
      const price = Number(key.match(/price=(\d+(\.\d+)?)/)![1]);

      let amount: number = 0;
      if (typeof value == 'number') {
        amount = value;
      }

      this.totalPrice += price * amount;
      this.stocks.push({
        id,
        symbol,
        name,
        price,
        amount,
      });
    }
  }
}
