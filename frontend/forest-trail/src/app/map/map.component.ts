import {Component, AfterViewInit, Input} from '@angular/core';
import * as L from 'leaflet';
import { MarkerService } from '../marker.service';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ControlPointDto} from "../controlpoint-dto";

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const greenImgMarker = 'assets/green-marker.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  private map: any;

  @Input()
  cpFromImg: ControlPointDto = {}

  @Input()
  blob: any;

  constructor(private markerService: MarkerService,
              private readonly sanitizer: DomSanitizer) {}

  private initMap(): void {
    this.map = L.map('map', {
      center: [ 54.32832, 13.462928 ],
      zoom: 9
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });


    tiles.addTo(this.map);
  }

  updateMap() {
    console.log(this.cpFromImg);
    var url: SafeUrl = "";
    // if (this.cpFromImg.latitude != null && this.cpFromImg.longitude != null) {

      // L.marker([ this.cpFromImg.latitude!, this.cpFromImg.longitude! ]).remove();
    this.displayImage().then(
      value => {
        console.log(value);
        url = value
      }
    );
    console.log(url);

    console.log(this.cpFromImg);
    console.log(this.cpFromImg.latitude);
    L.marker([ this.cpFromImg.latitude!, this.cpFromImg.longitude! ], {
        icon: L.icon({
          iconSize: [ 25, 41 ],
          iconAnchor: [ 13, 41 ],
          iconUrl: greenImgMarker,
          shadowUrl: shadowUrl
        })
      })
      .addTo(this.map)
      .bindPopup(`
            <img src="${url}" alt="img" style="width: 5%">
        `)
      .openPopup();

    //} else {
     // console.log("Did not find coordinates of CP");
    //}
  }

  async displayImage(): Promise<SafeUrl> {
    let objectURL = URL.createObjectURL(this.blob);
    return this.sanitizer.bypassSecurityTrustUrl(objectURL);
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.markerService.makeControlpointMarker(this.map);
  }

}
