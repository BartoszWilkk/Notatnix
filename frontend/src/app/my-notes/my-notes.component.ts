import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {GlobalConstants} from '../global-constants';

@Component({
  selector: 'app-my-notes',
  templateUrl: './my-notes.component.html',
  styleUrls: ['./my-notes.component.css']
})
export class MyNotesComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    if (!GlobalConstants.logged) {
      this.router.navigateByUrl('/app-login');
    }
  }

}
