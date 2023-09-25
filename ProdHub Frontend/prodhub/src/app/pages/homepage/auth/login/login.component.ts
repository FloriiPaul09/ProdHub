import { Component, OnInit} from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  f !: FormGroup;

  constructor (
    private userSrvc : UserService
   ) { }

  ngOnInit(): void {
    this.f = new FormGroup({
      username : new FormControl("", Validators.required),
      password : new FormControl("", Validators.required)
    });
  }

  submit(){
    this.userSrvc.login(this.f.value);
  }

}
