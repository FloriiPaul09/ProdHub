import { Component } from '@angular/core';
import { AuthServiceService } from '../service/auth-service.service';
import { IRegisterData } from '../interfaces/iregister-data';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent {

  constructor(
    private authSrvc : AuthServiceService
  ){}

    signinData : IRegisterData = {
      username : '',
      email : '',
      password : ''
    }

    register(){
      this.authSrvc.signUp(this.signinData)
      .subscribe(res => {
        alert(res.user.username);
      })
    }

}
