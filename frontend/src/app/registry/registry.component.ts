import { Component, OnInit } from '@angular/core';
import {GlobalConstants} from '../global-constants';
import {Router} from '@angular/router';
import {ApiServiceService} from '../service/api-service.service';
import {Credentials} from '../model/credentials';
import {User} from '../model/user';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent implements OnInit {
  username: string;
  email: string;
  password: string;
  usernameClass;
  emailClass;
  passwordClass;
  notEmptyData;
  loginAlreadyUsed;
  constructor(public constants: GlobalConstants, public router: Router, public dataBaseService: ApiServiceService) { }

  ngOnInit() {
    this.usernameClass = 'correct';
    this.passwordClass = 'correct';
    this.emailClass = 'correct';
    this.notEmptyData = true;
    this.loginAlreadyUsed = false;
  }

  checkIsUsernameEmpty(): boolean {
    let returnVal = true;
    if (this.username == null || this.username === '') {
      this.notEmptyData = false;
    } else {
      this.usernameClass = 'correct';
      returnVal = false;
    }
    // this.hideMessageNoteAlreadyExist = true;
    return returnVal;
  }

  checkIsEmailEmpty(): boolean {
    let returnVal = true;
    if (this.email == null || this.email === '') {
      this.notEmptyData = false;
    } else {
      this.emailClass = 'correct';
      returnVal = false;
    }
    return returnVal;
  }

  checkIsPasswordEmpty(): boolean {
    let returnVal = true;
    if (this.password == null || this.password === '') {
      this.notEmptyData = false;
    } else {
      this.passwordClass = 'correct';
      returnVal = false;
    }
    return returnVal;
  }

  checkIsDataEmpty() {
    this.notEmptyData = true;
    if (this.checkIsUsernameEmpty()) {
      this.usernameClass = 'empty';
    }
    if (this.checkIsPasswordEmpty()) {
      this.passwordClass = 'empty';
    }
    if (this.checkIsEmailEmpty()) {
      this.emailClass = 'empty';
    }
  }

  registry() {
    this.checkIsDataEmpty();
    if (this.notEmptyData) {
      const user: User = {
        id: null,
        username: this.username,
        emailAddress: this.email,
        password: this.password,
        role: 'user'
      };
      this.dataBaseService.registry(user).subscribe(
        res => {
          if (res == null) {
            this.loginAlreadyUsed = true;
          } else {
            this.loginAlreadyUsed = false;
            this.constants.login(res.id, 'user');
            this.router.navigateByUrl('/app-all-notes');
          }
        },
        err => {
          alert('Error w metodzie registry()');
        }
      );
    }
  }
}
