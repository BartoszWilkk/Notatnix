import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {GlobalConstants} from '../global-constants';
import {ApiServiceService} from '../service/api-service.service';
import {Note} from '../model/note';

@Component({
  selector: 'app-my-notes',
  templateUrl: './my-notes.component.html',
  styleUrls: ['./my-notes.component.css']
})
export class MyNotesComponent implements OnInit {
  notes: Note[] = [];

  constructor(public router: Router, public dataBaseService: ApiServiceService) { }

  ngOnInit() {
    if (!GlobalConstants.logged) {
      this.router.navigateByUrl('/app-login');
    } else {
      this.getMyNotes();
    }
  }

  getMyNotes() {
    this.dataBaseService.getMyNotes(GlobalConstants.user).subscribe(
      res => {
        this.notes = res;
      },
      error => {
        alert('Error w metodzie getMyNotes() w komponencie MyNotesComponent');
      }
    );
  }

  public openNote(id) {
    this.router.navigate(['/app-view-note', id]);
  }
}
