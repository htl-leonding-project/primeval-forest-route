import { Component, OnInit } from '@angular/core';
import {QuarkusBackendService} from "../quarkus-backend.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent {

  selectedFile: File;
  message = '';

  fileInfos: Observable<any>;

  constructor(private service: QuarkusBackendService) { }

  selectFile(event) {

  }

  uploadFile() {

  }
}
