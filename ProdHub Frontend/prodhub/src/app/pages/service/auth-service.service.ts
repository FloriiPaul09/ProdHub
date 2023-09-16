import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from 'src/environments/environment.development';
import { ILoginData } from '../interfaces/ilogin-data';
import { IAccessData } from '../interfaces/iaccess-data';
import { BehaviorSubject, catchError, map, tap, throwError } from 'rxjs';
import { IRegisterData } from '../interfaces/iregister-data';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  jwtHelper : JwtHelperService = new JwtHelperService();
  apiUrl = environment.apiUsers;

  private authSubject = new BehaviorSubject<null | IAccessData>(null);

  authLogoutTimer : any;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.restoreUser();
   }

  login(data : ILoginData){
    return this.http.post<IAccessData>(this.apiUrl + 'login', data)
    .pipe(tap(data =>{
      this.authSubject.next(data);
      localStorage.setItem('user', JSON.stringify(data));

      const expDate = this.jwtHelper
      .getTokenExpirationDate(data.accessToken) as Date;
      this.autoLogout(expDate);
    }))
  }

   restoreUser(){
    const userJson = localStorage.getItem('user');
    if(!userJson){
      return;
    }
    const user : IAccessData = JSON.parse(userJson);
    if(this.jwtHelper.isTokenExpired(user.accessToken)){
      return;
    }
    this.authSubject.next(user);
   }

   signUp(data : IRegisterData){
    return this.http.post<IAccessData>(this.apiUrl +'/register', data)

   }

   logout(){
    this.authSubject.next(null);
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
    if(this.authLogoutTimer){
      clearTimeout(this.authLogoutTimer);
    }
   }

   autoLogout(expDate : Date){
    const expMessage = expDate.getTime() - new Date().getTime();
    this.authLogoutTimer = setTimeout(() => {
      this.logout();
    }, expMessage);
   }

   errors(error : any){
    switch (error.error){
      case "Email and password are required":
        return throwError('Email and password are required');
        break;
      case "Email already exists":
        return throwError('Email already exists');
        break;
      case "Email format is not valid":
        return throwError('Email format is not valid');
        break;
      case "Cannot find user":
        return throwError('Cannot find user');
        break;
      return throwError('error');
        break;
    }
   }

}
