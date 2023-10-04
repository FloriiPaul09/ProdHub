import { UserService } from 'src/app/services/user.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { IAccessData } from '../interfaces/iaccess-data';

@Injectable()
export class JwtInterceptorInterceptor implements HttpInterceptor {
  private user: IAccessData | null = null;

  constructor(private userSrvc: UserService) {
    userSrvc.isUserLogged.subscribe(user => this.user = user);
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (this.user) {
      const reqClone = request.clone(
        {
          headers: request.headers.set('Authorization', 'Bearer ' + this.user.accessToken)
        }
      );
      console.log(this.user);
      console.log(reqClone.headers);
      return next.handle(reqClone);
    }
    return next.handle(request);
  }
}
