import {Component, AfterViewInit, Input, OnChanges, SimpleChanges} from '@angular/core';
import * as L from 'leaflet';
import { MarkerService } from '../marker.service';

import {GpxdataDto} from "../gpxdata-dto";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {ControlPointDto} from "../controlpoint-dto";
import {PictureDto} from "../picture-dto";

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
const imgUrl = "http://localhost:8080/api/picture/getImageById/";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit, OnChanges {

  private map: any;
  private check: Boolean = false;

  @Input()
  img: PictureDto = {};

  @Input()
  cpFromImg: ControlPointDto = {}

  constructor(private markerService: MarkerService,
              private readonly sanitizer: DomSanitizer) {}

  private initMap(): void {
    this.map = L.map('map', {
      center: [ 54.32832, 13.462928 ],
      zoom: 7.5
    });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });


    tiles.addTo(this.map);
    this.check = true;
  }

  updateMap() {
    if (this.img.id == null || this.cpFromImg.id == null) {
      return
    }
    const lat = this.cpFromImg.latitudeCoordinate;
    const lon = this.cpFromImg.longitudeCoordinate;
    var url = imgUrl + "" + this.img.id!;
    console.log(lon + " " + lat)
    // @ts-ignore
    L.marker([ lat, lon ], {
        icon: L.icon({
          iconSize: [ 25, 41 ],
          iconAnchor: [ 13, 41 ],
          iconUrl: greenImgMarker,
          shadowUrl: shadowUrl
        })
      })
      .addTo(this.map)
      .bindPopup(
        `<img src="${url}" alt="img" style="width: 300%">`,
        {}
      )
      .openPopup();
  }

  ngAfterViewInit(): void {
    this.initMap();
    this.markerService.makeControlpointMarker(this.map, 0)
  }

  changeMapView(id: GpxdataDto): void {
    this.markerService.makeControlpointMarker(this.map, id.id);
  }

  ngOnChanges(changes: SimpleChanges): void {
    for (const change1 in changes) {
      if (change1 == 'img' || change1 == 'cpFromImg') {
        console.log(changes)
        if (!changes[change1].firstChange) {
          this.updateMap()
        }
      }
    }
  }
}
