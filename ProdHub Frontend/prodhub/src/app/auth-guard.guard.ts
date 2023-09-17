import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { UserService } from './services/user.service';
import { Observable, map, take } from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class authGuardGuard implements CanActivate, CanActivateChild {

  constructor(
    private router : Router,
    private userSrvc : UserService
  ){}

    canActivate(
      route : ActivatedRouteSnapshot,
      state : RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      return this.userSrvc.isLoggedIn$.pipe(take(1), map(isLoggedIn => {
        if(isLoggedIn){
          return true;
        }
        this.router.navigate(['/homepage']);
        return false;
      }));
    }

    canActivateChild(
      childRoute : ActivatedRouteSnapshot,
      state : RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree{
      return this.canActivateChild(childRoute, state);
    }

}
