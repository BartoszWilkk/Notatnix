import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TagAllComponent } from './tag-all.component';

describe('TagAllComponent', () => {
  let component: TagAllComponent;
  let fixture: ComponentFixture<TagAllComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TagAllComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TagAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
