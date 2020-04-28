import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ActivatedRouteSnapshot, Route, Router} from '@angular/router';
import {Note} from '../model/note';
import {ApiServiceService} from '../service/api-service.service';
import {GlobalConstants} from '../global-constants';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import {FileModel} from '../model/fileModel';

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

  private displayingDiv;
  private title;
  private description;

  private titleClass;
  private descriptionClass;

  private notEmptyData;
  private noteAlreadyExistHidden = true;

  constructor(private dataBaseService: ApiServiceService, private route: ActivatedRoute, private router: Router) { }

  async ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    let userId;
    this.displayingDiv = 'hide-div';
    this.editModeHidden = true;
    this.viewModeHidden = false;
    this.noteAlreadyExistHidden = true;
    this.note = await this.dataBaseService.getNote(id).toPromise();
    this.title = this.note.title;
    this.description = this.note.description;
    userId = this.note.user;
    if (this.note.user !== GlobalConstants.user) {
      // this.router.navigateByUrl('/app-all-notes');
      document.getElementById('makeChanges').style.display = 'none';
    }
    // this.dataBaseService.getNote(id).toPromise().then(data => {
    //   this.note = data;
    //   this.title = this.note.title;
    //   this.description = this.note.description;
    //   userId = this.note.user;
    //   if (this.note.user !== GlobalConstants.user) {
    //     // this.router.navigateByUrl('/app-all-notes');
    //     document.getElementById('makeChanges').style.display = 'none';
    //   }
    // });
    // this.dataBaseService.getNote(id).subscribe(
    //   res => {
    //     this.note = res;
    //     this.title = this.note.title;
    //     this.description = this.note.description;
    //     userId = this.note.user;
    //     if (this.note.user !== GlobalConstants.user) {
    //       // this.router.navigateByUrl('/app-all-notes');
    //       document.getElementById('makeChanges').style.display = 'none';
    //     }
    //
    //   },
    //   err => {
    //     alert('An error occurred, cannot get note to view from database in ViewNoteComponent');
    //   }
    // );
    console.log('teraz umiem czekac: ' + this.note.id);
    this.dataBaseService.getFilesByNoteId(id).subscribe(
      res => {
        this.files = res;
      },
      error => {
        alert('Error w metodzie ngOnInit() w linii 63 w komponencie ViewNoteComponent');
      }
    );
  }

  // ngOnInit() {
  //   const id = this.route.snapshot.paramMap.get('id');
  //   let userId;
  //   this.displayingDiv = 'hide-div';
  //   this.editModeHidden = true;
  //   this.viewModeHidden = false;
  //   this.noteAlreadyExistHidden = true;
  //   this.dataBaseService.getNote(id).subscribe(
  //     res => {
  //       this.note = res;
  //       this.title = this.note.title;
  //       this.description = this.note.description;
  //       userId = this.note.user;
  //       if (this.note.user !== GlobalConstants.user) {
  //         // this.router.navigateByUrl('/app-all-notes');
  //         document.getElementById('makeChanges').style.display = 'none';
  //       }
  //
  //     },
  //     err => {
  //       alert('An error occurred, cannot get note to view from database in ViewNoteComponent');
  //     }
  //   );
  //   // alert(this.note.id);
  //   this.dataBaseService.getFilesByNoteId(id).subscribe(
  //     res => {
  //       this.files = res;
  //     },
  //     error => {
  //       alert('Error w metodzie ngOnInit() w linii 63 w komponencie ViewNoteComponent');
  //     }
  //   );
  // }

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
        averageRating: this.note.averageRating,
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
  //
  // getFile(id) {
  //   this.dataBaseService.get
  // }

  downloadFile(id: string) {
    const link = document.createElement('a');
    link.setAttribute('href', this.dataBaseService.DOWNLOAD_FILE + id);
    document.body.appendChild(link);
    link.click();
    link.remove();
  }

  // async getUrl(file): Promise<string> {
  //   const fileDownloaded = await this.dataBaseService.downloadFile2(file);
  //   // const url = URL.createObjectURL(fileDownloaded);
  //   return fileDownloaded;
  // }
}
