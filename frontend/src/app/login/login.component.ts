import { Component, OnInit } from '@angular/core';
import {GlobalConstants} from '../global-constants';
import {Router} from '@angular/router';
import {ApiServiceService} from '../service/api-service.service';
import {Note} from '../model/note';
import {Credentials} from '../model/credentials';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;
  usernameClass;
  passwordClass;
  correctCredentials;
  notEmptyData;

  constructor(public constants: GlobalConstants, public router: Router, public dataBaseService: ApiServiceService) {
  }

  ngOnInit() {
    this.usernameClass = 'correct';
    this.passwordClass = 'correct';
    this.notEmptyData = true;
    this.correctCredentials = true;
    this.loginAutomaticForDeveloper();
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
  }

  login() {
    this.checkIsDataEmpty();
    if (this.notEmptyData) {
      const credentials: Credentials = {
        username: this.username,
        password: this.password
      };
      this.dataBaseService.login(credentials).subscribe(
        res => {
          if (res == null) {
            this.correctCredentials = false;
          } else {
            this.correctCredentials = true;
            this.constants.login(res.id);
            this.router.navigateByUrl('/app-all-notes');
          }
        },
        err => {
          alert('Error w metodzie login()');
        }
      );
    }
  }

  loginAutomaticForDeveloper() {
    this.username = 'user';
    this.password = 'user';
    this.login();
  }
}
