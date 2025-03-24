import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginFormComponent} from './login-form/login-form.component';
import {DashboardComponent} from './dashboard/dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' }, // Redirect empty path to 'login'
  { path: 'login', component: LoginFormComponent }, // Explicit login route
  { path: 'dashboard', component: DashboardComponent },
  { path: '**', redirectTo: 'login' } // Redirect unknown paths to login
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
