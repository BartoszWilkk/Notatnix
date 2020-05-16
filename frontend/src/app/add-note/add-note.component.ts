import { Component, OnInit } from '@angular/core';
import {ApiServiceService} from '../service/api-service.service';
import {Note} from '../model/note';
import {Router} from '@angular/router';
import {GlobalConstants} from '../global-constants';
import {HttpEventType, HttpResponse} from '@angular/common/http';

@Component({
  selector: 'app-add-note',
  templateUrl: './add-note.component.html',
  styleUrls: ['./add-note.component.css']
})
export class AddNoteComponent implements OnInit {
  url: string;

  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
   title;
   description;
   tagTmp = '';
   tags: string[] = [];

   titleClass;
   descriptionClass;

   notEmptyData;
   hideMessageNoteAlreadyExist;

  constructor(public dataBaseService: ApiServiceService, public router: Router) { }

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

  async submit() {
    this.checkIsDataEmpty();
    if (this.notEmptyData) {
      const note: Note = {
        id: null,
        user: GlobalConstants.user,
        title: this.title,
        description: this.description,
        userName: null,
        averageRating: null,
        files: null,
        tags: this.tags
      };
      console.log(GlobalConstants.user);
      const noteSaved = await this.dataBaseService.saveNote(note).toPromise();
      if (noteSaved != null) {
        this.upload(noteSaved.id);
        await this.router.navigateByUrl('/app-all-notes');
      } else {
        alert('Nie udalo sie zapisac nowej notatki -> async submit() w komponencie AddNoteComponent');
        await this.router.navigateByUrl('/app-all-notes');
      }
    }
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
    this.url = URL.createObjectURL(event.target.files[0]);
    console.log(this.url);
  }

  upload(id) {
    if (this.selectedFiles != null) {
      this.progress.percentage = 0;
      this.currentFileUpload = this.selectedFiles.item(0);
      this.dataBaseService.saveFile(this.currentFileUpload, id).subscribe(event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            alert('File Successfully Uploaded');
          }
          this.selectedFiles = undefined;
        }
      );
    }
  }

  addOneTagToTagsList(event) {
    if (event.key === 'Enter') {
      if (this.tagTmp !== '') {
        if (!this.tags.includes(this.tagTmp)) {
          this.tags.push(this.tagTmp);
          this.tagTmp = '';
        } else {
          alert('Ten tag jest już dodany do notatki');
        }
      }
    }
  }
}
