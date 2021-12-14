import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as L from 'leaflet';

import {ControlPointDto} from "./controlpoint-dto";
import {CoordinatesDto} from "./coordinates-dto";
import {QuarkusBackendService} from "./quarkus-backend.service";

@Injectable({
  providedIn: 'root'
})
export class MarkerService {

  jsonArray: [] = [];
  controlpoints: ControlPointDto[] = [];
  coordinates: CoordinatesDto[] = [];

  constructor(public quarkusService: QuarkusBackendService,
              private http: HttpClient) { }

  public makeControlpointMarker(map: L.Map) {

    this.getControlPoints(map);
    this.getCoordinates(map)


  }

  getControlPoints(map: L.Map) {
    this.quarkusService.getAllControlPoints()
      .subscribe(r => {
        if (r != null) {
          //console.log(JSON.stringify(r));
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const cpElement of this.jsonArray) {
            this.controlpoints.push({
              id: cpElement["id"],
              latitude: cpElement["latitudeCoordinate"],
              longitude: cpElement["longitudeCoordinate"],
              name: cpElement["name"]
            })
          }
        }
        for (let i = 0; i < this.controlpoints.length; i++) {
          const lat = this.controlpoints[i].latitude;
          const lon = this.controlpoints[i].longitude;

          // @ts-ignore
          const marker = L.marker([lat, lon]);

          marker.addTo(map);
        }
      })
  }

  getCoordinates(map: L.Map) {
    this.quarkusService.getAllCoordinates()
      .subscribe(c => {
        if (c != null) {
          //console.log(JSON.stringify(c));
          this.jsonArray = JSON.parse(JSON.stringify(c));
          for (const coElement of this.jsonArray) {
            this.coordinates.push({
              latitude: coElement["latitude"],
              longitude: coElement["longitude"]
            })
          }
        }
        var array: L.LatLngExpression[] | L.LatLngExpression[][] =  [];
        this.coordinates.forEach(item => {
          if (item.latitude != null) {
            if (item.longitude != null) {
              // @ts-ignore
              array.push(new L.LatLng(item.latitude, item.longitude));
            }
          }
        });
        var poly = L.polyline(array, {
          color: 'purple',
          weight: 5,
          opacity: 1,
          smoothFactor: 1
        })
        poly.addTo(map);
      })
  }
}
