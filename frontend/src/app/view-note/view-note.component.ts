import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, Route, Router} from '@angular/router';
import {Note} from '../model/note';
import {ApiServiceService} from '../service/api-service.service';
import {GlobalConstants} from '../global-constants';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {FileModel} from '../model/fileModel';
import {RateId} from '../model/rate-id';

@Component({
  selector: 'app-view-note',
  templateUrl: './view-note.component.html',
  styleUrls: ['./view-note.component.css']
})
export class ViewNoteComponent implements OnInit {
  note: Note;
  editModeHidden = true;
  viewModeHidden = false;
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };
  files: FileModel[] = [];
  myRate = '';

  tagTmp = '';
  selectedRate;

  displayingDiv;
  title;
  description;

  titleClass;
  descriptionClass;

  notEmptyData;
  noteAlreadyExistHidden = true;

  constructor(public dataBaseService: ApiServiceService, public route: ActivatedRoute, public router: Router) { }

  async ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.displayingDiv = 'hide-div';
    this.editModeHidden = true;
    this.viewModeHidden = false;
    this.noteAlreadyExistHidden = true;
    this.note = await this.dataBaseService.getNote(id).toPromise();
    this.title = this.note.title;
    this.description = this.note.description;
    const userIdLocal = this.note.user;
    document.getElementById('place-for-rate').hidden = true;
    if (!GlobalConstants.logged) {
      document.getElementById('add-rate-button').hidden = true;
    }
    if (this.note.user !== GlobalConstants.user) {
      // this.router.navigateByUrl('/app-all-notes');
      document.getElementById('makeChanges').style.display = 'none';
    } else {
      document.getElementById('add-rate-button').hidden = true;
    }
    if (!GlobalConstants.logged) {
      document.getElementById('add-rate-button').style.display = 'none';
    } else {
      const rateId: RateId = {
        noteId: id,
        userId: GlobalConstants.user
      };
      this.dataBaseService.getMyRate(rateId).subscribe(
        res => {
          if (res != null && res !== '') {
            this.myRate = res;
          }
        },
        err => {
          alert('Error w metodzie ngOnInit() w ViewNoteComponent');
        }
      );
    }
    this.dataBaseService.getFilesByNoteId(id).subscribe(
      res => {
        this.files = res;
      },
      error => {
        alert('Error w metodzie ngOnInit() w linii 63 w komponencie ViewNoteComponent');
      }
    );
  }

  editNote() {
    this.viewModeHidden = true;
    this.editModeHidden = false;
    this.displayingDiv = 'dont-hide';
  }

  checkIsTitleEmpty(): boolean {
    let returnVal = true;
    if (this.title == null || this.title === '') {
      this.notEmptyData = false;
    } else {
      this.titleClass = 'correct';
      returnVal = false;
    }
    this.noteAlreadyExistHidden = true;
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

  saveChanges() {
    this.upload();
    this.checkIsDataEmpty();
    if (this.notEmptyData) {
      const editedNote: Note = {
        id: this.note.id,
        user: this.note.user,
        title: this.title,
        description: this.description,
        userName: this.note.userName,
        averageRating: this.note.averageRating,
        tags: this.note.tags,
        // tutaj trzeba sprawdzic o co biega
        files: this.note.files
      };
      let checkTitle = true;
      if (this.note.title === this.title) {
        checkTitle = false;
      }
      this.dataBaseService.editNote(editedNote, checkTitle).subscribe(
        res => {
          if (res == null) {
            this.noteAlreadyExistHidden = false;
          } else {
            alert('Pomyślnie edytowano notatkę');
            this.viewModeHidden = false;
            this.editModeHidden = true;
            this.displayingDiv = 'hide-div';
            this.ngOnInit();
          }
        },
        err => {
          alert('Error w metodzie saveChanges() w komponencie ViewNoteComponent');
        }
      );
    }
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    if (this.selectedFiles != null) {
      this.progress.percentage = 0;
      this.currentFileUpload = this.selectedFiles.item(0);
      this.dataBaseService.saveFile(this.currentFileUpload, this.note.id).subscribe(event => {
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

  downloadFile(id: string) {
    const link = document.createElement('a');
    link.setAttribute('href', this.dataBaseService.DOWNLOAD_FILE + id);
    document.body.appendChild(link);
    link.click();
    link.remove();
  }

  addOneTagToTagsList(event) {
    if (event.key === 'Enter') {
      if (this.tagTmp !== '') {
        if (!this.note.tags.includes(this.tagTmp)) {
          this.note.tags.push(this.tagTmp);
          this.tagTmp = '';
        } else {
          alert('Ten tag jest już dodany do notatki');
        }
      }
    }
  }

  deleteTag(tag: string) {
    const index = this.note.tags.indexOf(tag, 0);
    if (index > -1) {
      this.note.tags.splice(index, 1);
    }
  }

  abortChanges() {
    this.ngOnInit();
  }

  addRate() {
    document.getElementById('add-rate-button').hidden = true;
    document.getElementById('place-for-rate').hidden = false;

  }

  saveRate(rateValue: string) {
    const rate = {
      userId: GlobalConstants.user,
      noteId: this.note.id,
      rate: rateValue
    };
    this.dataBaseService.saveRate(rate).subscribe(res => {
      this.note.averageRating = res;
    }, error => {
      alert('Error w metodzie saveRate(rateValue: string) w ViewNoteComponent');
    });
  }

  confirmRate() {
    const rateVal: string = this.selectedRate;
    document.getElementById('add-rate-button').hidden = false;
    document.getElementById('place-for-rate').hidden = true;
    this.saveRate(rateVal);
  }

  deleteNote() {
    this.router.navigateByUrl('/app-all-notes');
    this.dataBaseService.deleteNote(this.note.id).subscribe(res => {
    }, error => {
      alert('Error w metodzie deleteNote() w ViewNoteComponent');
    });
  }
}
