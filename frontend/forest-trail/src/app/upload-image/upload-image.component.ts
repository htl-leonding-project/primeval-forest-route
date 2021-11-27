import { Component, OnInit } from '@angular/core';
import {QuarkusBackendService} from "../quarkus-backend.service";

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent {

  selectedFile?: ImageSnippet;
  message = '';
  fileValid: boolean = false;

  constructor(private service: QuarkusBackendService) {}

  processFile(imageInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (ev: any) => {
      this.selectedFile = new ImageSnippet(ev.target.result, file);
      this.fileValid = !this.fileValid;
    });
    reader.readAsDataURL(file);

  }

  uploadFile() {
    if (this.fileValid) {
      this.service.uploadImage(this.selectedFile!.file).subscribe(
        (err) => {
          console.log(err);
        }
      );
    }
  }
}

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
