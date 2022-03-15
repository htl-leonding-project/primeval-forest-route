import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadXmlComponent } from './upload-xml.component';

describe('UploadXmlComponent', () => {
  let component: UploadXmlComponent;
  let fixture: ComponentFixture<UploadXmlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadXmlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UploadXmlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
