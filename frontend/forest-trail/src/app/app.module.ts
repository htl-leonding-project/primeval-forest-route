import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import { MapComponent } from './map/map.component';
import { UploadImageComponent } from './upload-image/upload-image.component';

import { MarkerService } from './marker.service';
import { RouteMenuComponent } from './route-menu/route-menu.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    UploadImageComponent,
    RouteMenuComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        LeafletModule,
        FormsModule
    ],
  providers: [
    RouteMenuComponent,
    MapComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
