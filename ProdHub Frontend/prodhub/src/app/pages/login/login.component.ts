import { Component } from '@angular/core';
import { ILoginData } from '../interfaces/ilogin-data';
import { AuthServiceService } from '../service/auth-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(
    private authSrvc : AuthServiceService,
    private router : Router
  ){}

  loginData : ILoginData = {
    username : '',
    password : ''
  }

  login(){
    this.authSrvc.login(this.loginData)
    .subscribe(res => {
      this.router.navigate(['/home']);
    })
  }




}
