import {Injectable, Input, Output} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as L from 'leaflet';

import {CoordinatesDto} from "./coordinates-dto";
import {QuarkusBackendService} from "./quarkus-backend.service";
import {ControlPointDto} from "./controlpoint-dto";
import {RouteMenuComponent} from "./route-menu/route-menu.component";
import {GpxdataDto} from "./gpxdata-dto";
import {marker} from "leaflet";

@Injectable({
  providedIn: 'root'
})
export class MarkerService {

  markers: any = [];
  prevPoly: any = [];
  jsonArray: [] = [];
  controlpoints: ControlPointDto[] = [];
  coordinates: CoordinatesDto[] = [];
  // @Input()
  // gpxdata: number = 0;
  private check: boolean = false;

  constructor(public quarkusService: QuarkusBackendService) { }

  public makeControlpointMarker(map: L.Map, gpxData: GpxdataDto) {
    var id = gpxData.id;
    var cp: ControlPointDto[] = [];
    var mk: any = [];
    var co: CoordinatesDto[] = [];
    var py: any = [];

    if(gpxData.id != 0 || null || undefined) {
      if(this.check) {
        this.clearMap(map)
      }
      this.getCoordinatesById(map, id, co, py)
      this.getControlPointsById(map, id, cp, mk)
    }
    this.check = true
  }

  clearMap(map: L.Map) {
    this.markers.forEach(function (item: L.Layer) {
      map.removeLayer(item)
    })
    this.prevPoly.forEach(function (item: L.Layer) {
      map.removeLayer(item)
    })
  }

  getControlPointsById(map: L.Map,
                       id: number | null | undefined,
                       cp: ControlPointDto[],
                       mk: any) {
    this.quarkusService.getControlPointsById(id)
      .subscribe(r => {
        if (r != null) {
          //console.log(JSON.stringify(r));
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const cpElement of this.jsonArray) {
            cp.push({
              latitude: cpElement["latitudeCoordinate"],
              longitude: cpElement["longitudeCoordinate"]
            })
          }
        }
        this.controlpoints = cp;
        for (let i = 0; i < this.controlpoints.length; i++) {
          const lat = this.controlpoints[i].latitude;
          const lon = this.controlpoints[i].longitude;

          // @ts-ignore
          const marker = L.marker([lat, lon]).addTo(map);
          mk.push(marker)
        }

        this.markers = mk;
      })
  }

  getCoordinatesById(map: L.Map,
                     id: number | null | undefined,
                     co: CoordinatesDto[],
                     py: any) {
    // @ts-ignore
    this.quarkusService.getCoordinatesById(id)
      .subscribe(c => {
        if (c != null) {
          //console.log(JSON.stringify(c));
          this.jsonArray = JSON.parse(JSON.stringify(c));
          for (const coElement of this.jsonArray) {
            co.push({
              latitude: coElement["latitude"],
              longitude: coElement["longitude"]
            })
          }
        }
        var array: L.LatLngExpression[] | L.LatLngExpression[][] =  [];
        this.coordinates = co;
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
        }).addTo(map);
        py.push(poly);
        this.prevPoly = py;

        if(id == 1) {
          const lat = 54.367342;
          const lon = 13.5652;
          map.flyTo([lat, lon], 7.5)
        } else {
          const lat = this.coordinates[this.coordinates.length/2].latitude;
          const lon = this.coordinates[this.coordinates.length/2].longitude;
          // @ts-ignore
          map.flyTo([lat, lon], 7.5)
        }
      })
  }
}
