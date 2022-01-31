import {Component, Input, OnInit} from '@angular/core';
import {PictureDto} from "../picture-dto";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

@Component({
  selector: 'app-load-image',
  templateUrl: './load-image.component.html',
  styleUrls: ['./load-image.component.css']
})
export class LoadImageComponent {

  @Input()
  blob: any;

  imageUrl: SafeUrl;

  constructor(private readonly sanitizer: DomSanitizer) {
    this.imageUrl = "";
  }

  displayImage() {
    let objectURL = URL.createObjectURL(this.blob);
    this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    console.log(this.imageUrl);
  }

}
