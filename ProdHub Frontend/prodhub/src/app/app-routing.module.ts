import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './pages/homepage/auth/register/register.component';
import { LoginComponent } from './pages/homepage/auth/login/login.component';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { UserpageComponent } from './pages/userpage/userpage.component';

const routes: Routes = [
  {path: '', redirectTo: '/homepage', pathMatch: 'full'},
  {path: 'homepage', component: HomepageComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'userpage', component: UserpageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
