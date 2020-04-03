import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {ApiServiceService} from './service/api-service.service';
import { AllNotesComponent } from './all-notes/all-notes.component';
import { AddNoteComponent } from './add-note/add-note.component';
import {RouterModule, Routes} from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';



const appRoutes: Routes = [
  {
    path: 'app-all-notes',
    component: AllNotesComponent
  },
  {
    path: 'app-add-note',
    component: AddNoteComponent
  },
  {
    path: '',
    redirectTo: '/app-all-notes',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    AllNotesComponent,
    AddNoteComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true}
    ),
    FormsModule
  ],
  providers: [
    ApiServiceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
