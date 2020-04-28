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

  async submit() {
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
      // this.dataBaseService.saveNote(note).subscribe(
      //   res => {
      //     if (res == null) {
      //       this.hideMessageNoteAlreadyExist = false;
      //     } else {
      //       this.router.navigateByUrl('/app-all-notes');
      //     }
      //   },
      //   err => {
      //     alert('Error w metodzie submit() w komponencie AddNoteComponent');
      //   }
      // );
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
    // const input = document.getElementById('customFile');
    // this.url = URL.createObjectURL(event.target.files);

    // if (event.target.files && event.target.files[0]) {
    //   const reader = new FileReader();
    //
    //   reader.readAsDataURL(event.target.files[0]); // read file as data url
    //   reader.onload = (event) => { // called once readAsDataURL is completed
    //     this.url = event.target.result;
    //   };
    // }
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

}
