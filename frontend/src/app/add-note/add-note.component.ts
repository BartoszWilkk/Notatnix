import { Component, OnInit } from '@angular/core';
import {ApiServiceService} from '../service/api-service.service';
import {Note} from '../model/note';
import {Router} from '@angular/router';
import {GlobalConstants} from '../global-constants';

@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.component.html',
  styleUrls: ['./add-note.component.css']
})
export class AddNoteComponent implements OnInit {

  private title;
  private description;

  private titleClass;
  private descriptionClass;

  private notEmptyData;
  private hideMessageNoteAlreadyExist;

  constructor(private dataBaseService: ApiServiceService, private router: Router) { }

  ngOnInit() {
    if (!GlobalConstants.logged) {
      this.router.navigateByUrl('/app-login');
    }
    this.titleClass = 'correct';
    this.descriptionClass = 'correct';
    this.notEmptyData = true;
    this.hideMessageNoteAlreadyExist = true;
  }

  checkIsTitleEmpty(): boolean {
    let returnVal = true;
    if (this.title == null || this.title === '') {
      this.notEmptyData = false;
    } else {
      this.titleClass = 'correct';
      returnVal = false;
    }
    this.hideMessageNoteAlreadyExist = true;
    return returnVal;
  }

  checkIsDescriptionEmpty(): boolean {
    let returnVal = true;
    if (this.description == null || this.description === '') {
      this.notEmptyData = false;
    } else {
      this.descriptionClass = 'correct';
      returnVal = false;
    }
    return returnVal;
  }

  checkIsDataEmpty() {
    this.notEmptyData = true;
    if (this.checkIsTitleEmpty()) {
      this.titleClass = 'empty';
    }
    if (this.checkIsDescriptionEmpty()) {
      this.descriptionClass = 'empty';
    }
  }

  submit() {
    this.checkIsDataEmpty();
    if (this.notEmptyData) {
      const note: Note = {
        id: null,
        user: GlobalConstants.user,
        title: this.title,
        description: this.description,
        averageRating: null,
        files: null
      };
      console.log(GlobalConstants.user);
      this.dataBaseService.saveNote(note).subscribe(
        res => {
          if (res == null) {
            this.hideMessageNoteAlreadyExist = false;
          } else {
            this.router.navigateByUrl('/app-all-notes');
          }
        },
        err => {
          alert('Error w metodzie submit() w komponencie AddNoteComponent');
        }
      );
    }
  }

}
