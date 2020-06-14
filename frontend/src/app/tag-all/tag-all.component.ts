import { Component, OnInit } from '@angular/core';
import {Tag} from '../model/tag';
import {ApiServiceService} from '../service/api-service.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-tag-all',
  templateUrl: './tag-all.component.html',
  styleUrls: ['./tag-all.component.css']
})
export class TagAllComponent implements OnInit {

  tags: Tag[] = null;

  constructor(public dataBaseService: ApiServiceService, public router: Router) { }

  ngOnInit() {
    this.dataBaseService.getAllTags().subscribe(
      res => {
        this.tags = res;
      },
      err => {
        alert('An error has occurred in method ngOnInit() in TagAllComponent');
      }
    );
  }

  showFilteredNotes(tagName: string) {
    this.router.navigate(['/app-filter', tagName]);
  }
}
