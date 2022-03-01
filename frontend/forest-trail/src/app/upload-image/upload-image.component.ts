import {Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {QuarkusBackendService} from "../quarkus-backend.service";
import {PictureDto} from "../picture-dto";
import {ControlPointDto} from "../controlpoint-dto";
import {MapComponent} from "../map/map.component";

@Component({
  selector: 'app-upload-image',
  templateUrl: './upload-image.component.html',
  styleUrls: ['./upload-image.component.css']
})
export class UploadImageComponent {

  selectedFile?: ImageSnippet;
  message = '';
  fileValid: boolean = false;

  @Output()
  sentImg: PictureDto = {};

  @ViewChild(MapComponent)
  mapComp!: MapComponent;

  @Output()
  cp: ControlPointDto = {}

  constructor(private service: QuarkusBackendService) {
  }

  processFile(imageInput: any) {
    this.fileValid = false;
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
              this.getCpId().then(value => {
                console.log(value);
                this.cp = value
              })
            }
          )
        }
        console.log(this.sentImg);
      })
    }
  }
  async getCpId(): Promise<ControlPointDto> {
    if (this.sentImg.id != null) {
      return await this.service.getCpWithImageId(this.sentImg.id).toPromise();
    }
    return Promise.reject("no controlpoint to this image found");
  }
}

class ImageSnippet {
  constructor(public src: string, public file: File) {}
}
