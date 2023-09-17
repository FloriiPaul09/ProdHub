import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  f!:FormGroup;

  constructor (
    private userSrvc : UserService,
    private router : Router
  ) {}

    ngOnInit(): void {
      this.f = new FormGroup({
        username : new FormControl("", Validators.required),
        name : new FormControl("", Validators.required),
        surname : new FormControl("", Validators.required),
        email : new FormControl("", Validators.required),
        password : new FormControl("", Validators.required)
      });
    }

  register(){
    this.userSrvc.signUp(this.f.value)
    .subscribe(accessData => {
      this.router.navigate(['/login'])
    })
  }

}
