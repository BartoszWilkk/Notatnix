import { Component, OnInit } from '@angular/core';
import {ApiServiceService} from '../../api-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  home;

  constructor(private apiService: ApiServiceService) { }

  ngOnInit() {
    this.apiService.getHome().subscribe(
      res => {
        this.home = res;
      },
      error => {
        alert('An error in home.component!');
      }
    );
  }

}
