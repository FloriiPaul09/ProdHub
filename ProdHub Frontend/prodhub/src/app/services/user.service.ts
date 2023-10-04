import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, tap, throwError } from 'rxjs';
import { IAccessData } from '../interfaces/iaccess-data';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment.development';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ILoginData } from '../interfaces/ilogin-data';
import { IRegisterData } from '../interfaces/iregister-data';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private logUser = new BehaviorSubject<null | IAccessData>(null);
  isUserLogged = this.logUser.asObservable();
  private storageUser:IAccessData;

  constructor (
    private http : HttpClient,
    private router : Router
    ) {
      this.storageUser=JSON.parse(localStorage.getItem('user')!);
      if(this.storageUser) this.logUser.next(this.storageUser);
    }

    signUp(data : IRegisterData){
      return this.http.post(environment.apiSignUp, data);

    }

    login(data : ILoginData){
      this.http.post<IAccessData>(environment.apiLogin, data)
      .subscribe(data => {

        const updatedData: IAccessData ={
          ...data,
          username: data.username,
        };

        this.logUser.next(updatedData);
        localStorage.setItem("user", JSON.stringify(data));
        this.router.navigate(["/userpage"]);
      })
    }

    logout(){
      this.logUser.next(null);
      localStorage.removeItem("user");
      this.router.navigate(["/homepage"])
    }


}
