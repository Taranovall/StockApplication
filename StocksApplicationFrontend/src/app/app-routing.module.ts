import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignInComponent} from "./sign-in/sign-in.component";
import {ProfileComponent} from "./profile/profile.component";
import {SignUpComponent} from "./sign-up/sign-up.component";
import {AuthorizedUserGuard} from "./guards/authorized-user.guard";
import {StocksComponent} from "./stocks/stocks.component";
import {NotAuthorizedUserGuard} from "./guards/not-authorized-user.guard";

const routes: Routes = [
  {path:'sign_in', component: SignInComponent, canActivate: [AuthorizedUserGuard]},
  {path:'sign_up', component: SignUpComponent, canActivate: [AuthorizedUserGuard]},
  {path:'profile', component: ProfileComponent, canActivate: [NotAuthorizedUserGuard]},
  {path:'', component: StocksComponent, canActivate: [NotAuthorizedUserGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
