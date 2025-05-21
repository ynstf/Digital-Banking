import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {AccountsComponent} from "./accounts/accounts.component";
import {NewCustomerComponent} from "./new-customer/new-customer.component";
import {CustomerAccountsComponent} from "./customer-accounts/customer-accounts.component";
import { LoginComponent } from './login/login.component';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import { AuthenticationGuard } from './guards/authentication.guard';
import { AuthorizationGuard } from './guards/authorization.guard';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { EditCustomerComponent } from './edit-customer/edit-customer.component';
import { CreateCurrentAccountComponent } from './pages/create-current-account/create-current-account.component';
import { CreateSavingAccountComponent } from './pages/create-saving-account/create-saving-account.component';
import { SettingsComponent } from './settings/settings.component';

const routes: Routes = [
  { path :"login", component : LoginComponent},
  { path :"", redirectTo : "/login", pathMatch:"full"},
  { path :"admin", component : AdminTemplateComponent, canActivate : [AuthenticationGuard] ,
    children : [
      { path :"customers", component : CustomersComponent},
      { path :"accounts", component : AccountsComponent},
      { path :"new-customer", component : NewCustomerComponent, canActivate: [AuthorizationGuard], data : {role : "ADMIN"} },
      { path :"customer-accounts/:id", component : CustomerAccountsComponent},
      { path :"notAuthorized", component : NotAuthorizedComponent},
      
  ]},
  { path: 'edit-customer/:id', component: EditCustomerComponent },
  { path: 'customer-accounts/:id', component: CustomerAccountsComponent },
  { path: 'accounts/create-current', component: CreateCurrentAccountComponent },
  { path: 'accounts/create-saving', component: CreateSavingAccountComponent },
  { path: 'profile/settings', component: SettingsComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
