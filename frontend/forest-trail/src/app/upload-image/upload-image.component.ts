import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {QuarkusBackendService} from "../quarkus-backend.service";
import {PictureDto} from "../picture-dto";
import {Observable} from "rxjs";

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent {

  selectedFile?: ImageSnippet;
  message = '';
  fileValid: boolean = false;

  sentImg: PictureDto = {};
  file: any;

  @Output()
  img: EventEmitter<any> = new EventEmitter<any>();

  images: PictureDto[] = [];

  constructor(private service: QuarkusBackendService) {  }

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
      this.service.uploadImage(this.selectedFile!.file).subscribe(res => {
        res = JSON.parse(JSON.stringify(res));
        this.sentImg = {
          id: res['id'],
          fileName: res['fileName']
        }
        if (this.sentImg.id != null) {
          this.service.getImage(this.sentImg.id).subscribe(
            f => {
              console.log(f)
              this.file = f
            }
          )
        }
        console.log(this.sentImg);
      })
    }
  }
}

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
