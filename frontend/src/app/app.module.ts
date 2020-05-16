import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AngularFireModule } from '@angular/fire';
import {AngularFireDatabase, AngularFireDatabaseModule} from '@angular/fire/database';
import {HttpClientModule} from '@angular/common/http';
import {ApiServiceService} from './service/api-service.service';
import { AllNotesComponent } from './all-notes/all-notes.component';
import { AddNoteComponent } from './add-note/add-note.component';
import {RouterModule, Routes} from '@angular/router';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { EditNoteComponent } from './edit-note/edit-note.component';
import { ViewNoteComponent } from './view-note/view-note.component';
import { RegistryComponent } from './registry/registry.component';
import { LoginComponent } from './login/login.component';
import { MyNotesComponent } from './my-notes/my-notes.component';
import {NgxDocViewerModule} from 'ngx-doc-viewer';
import { TagAllComponent } from './tag-all/tag-all.component';

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
    path: 'app-edit-note',
    component: EditNoteComponent
  },
  {
    path: 'app-registry',
    component: RegistryComponent
  },
  {
    path: 'app-login',
    component: LoginComponent
  },
  {
    path: 'app-my-notes',
    component: MyNotesComponent
  },
  {
    path: 'app-tag-all',
    component: TagAllComponent
  },
  {
    path: 'app-view-note/:id',
    component: ViewNoteComponent
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
    PageNotFoundComponent,
    EditNoteComponent,
    ViewNoteComponent,
    RegistryComponent,
    LoginComponent,
    MyNotesComponent,
    TagAllComponent
  ],
  imports: [
    BrowserModule,
    AngularFireDatabaseModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing: true}
    ),
    FormsModule,
    [NgxDocViewerModule]
  ],
  providers: [
    ApiServiceService
  ],
  bootstrap: [AppComponent],
  exports: [
  ]
})
export class AppModule { }
