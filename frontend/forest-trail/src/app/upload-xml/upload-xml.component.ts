import {Component, OnChanges, SimpleChanges} from '@angular/core';
import {QuarkusBackendService} from "../quarkus-backend.service";
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {MatInput} from '@angular/material/input';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RouteDto} from "../route-dto";
import {GpxdataDto} from "../gpxdata-dto";

@Component({
  selector: 'app-upload-xml',
  templateUrl: './upload-xml.component.html',
  styleUrls: ['./upload-xml.component.css']
})
export class UploadXmlComponent {

  constructor(
    public dialog: MatDialog
  ) {
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogRouteWindow, {
      width: '350px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}

class XmlFile {
  constructor(public src: string, public file: File) {
  }
}

@Component({
  selector: 'dialog-route',
  templateUrl: 'dialog-route.html',
})
export class DialogRouteWindow {

  routeForm: FormGroup = new FormGroup({
    name: new FormControl('', Validators.required),
    length: new FormControl('', Validators.required),
    file: new FormControl(null, Validators.requiredTrue)
  })

  selectedFile?: XmlFile
  fileValid: boolean = false;
  routeName: string;
  gpxData: GpxdataDto = {}

  uploadedRoutes: RouteDto[] = [];

  constructor(
    public dialogRef: MatDialogRef<DialogRouteWindow>,
    private readonly service: QuarkusBackendService) {
    this.routeName = ''
  }

  async uploadXmlFile() {
    console.log(this.routeForm.get('name')?.value);
    if (this.fileValid && this.routeForm.get('name')?.value != '') {
      await this.service.uploadXml(
        this.selectedFile!.file,
        this.routeForm.get('name')?.value
      ).then(
        res => {
          this.gpxData = res
          console.log(this.gpxData);

          this.service.uploadRoute(
            {
              name: this.routeForm.get('name')?.value,
              length: this.routeForm.get('length')?.value
            },
            this.gpxData.id!
          ).then(
            r => {
              console.log(r);
              this.uploadedRoutes.push(r)
            }
          )
        }
      )
    }
  }

  processFile(imageInput: any) {
    this.fileValid = false;
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (ev: any) => {
      this.selectedFile = new XmlFile(ev.target.result, file);
      this.fileValid = !this.fileValid;
    });
    reader.readAsDataURL(file);
  }
}
