import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import { MapComponent } from './map/map.component';
import { UploadImageComponent } from './upload-image/upload-image.component';

import { MarkerService } from './marker.service';
import { RouteMenuComponent } from './route-menu/route-menu.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {DialogRouteWindow, UploadXmlComponent} from './upload-xml/upload-xml.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatFormFieldModule} from "@angular/material/form-field";

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    UploadImageComponent,
    RouteMenuComponent,
    UploadXmlComponent,
    DialogRouteWindow
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    LeafletModule,
    FormsModule,
    MatDialogModule,
    NoopAnimationsModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule
  ],
  providers: [
    RouteMenuComponent,
    MapComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
