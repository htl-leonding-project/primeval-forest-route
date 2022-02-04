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

  private map: any;
  markers: any = [];
  prevPoly: any = [];
  jsonArray: [] = [];
  controlpoints: ControlPointDto[] = [];
  coordinates: CoordinatesDto[] = [];
  allCpoints: ControlPointDto[] = [];
  allCoords: CoordinatesDto[] = [];
  // @Input()
  // gpxdata: number = 0;
  private check: boolean = false;

  constructor(public quarkusService: QuarkusBackendService) { }

  public makeControlpointMarker(map: L.Map, id: number | null | undefined) {
    var cp: ControlPointDto[] = [];
    var mk: any = [];
    var co: CoordinatesDto[] = [];
    var py: any = [];

    if(id != 0 || null || undefined) {
      if(this.check) {
        this.clearMap(this.map)
      }
      this.getCoordinatesById(this.map, id, co, py)
      this.getControlPointsById(this.map, id, cp, mk)
    } else {
      this.map = map;
      this.getAllControlPoints(this.map, mk)
      this.getAllCoordinates(this.map, py)
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
              latitudeCoordinate: cpElement["latitudeCoordinate"],
              longitudeCoordinate: cpElement["longitudeCoordinate"]
            })
          }
        }
        this.controlpoints = cp;
        console.log(this.controlpoints)
        for (let i = 0; i < this.controlpoints.length; i++) {
          const lat = this.controlpoints[i].latitudeCoordinate!;
          const lon = this.controlpoints[i].longitudeCoordinate!;

          // @ts-ignore
          const marker = L.marker([lat, lon]).addTo(map);
          mk.push(marker)
        }

        this.markers = mk;
      })
  }


  getAllControlPoints(map: L.Map, mk: any) {
    this.quarkusService.getAllControlPoints()
      .subscribe(r => {
        if (r != null) {
          //console.log(JSON.stringify(r));
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const cpElement of this.jsonArray) {
            this.allCpoints.push({
              latitudeCoordinate: cpElement["latitudeCoordinate"],
              longitudeCoordinate: cpElement["longitudeCoordinate"]
            })
          }
        }
        console.log(this.allCpoints)
        for (let i = 0; i < this.allCpoints.length; i++) {
          const lat = this.allCpoints[i].latitudeCoordinate;
          const lon = this.allCpoints[i].longitudeCoordinate;

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
          if (item.latitude != null && item.longitude != null) {
            // @ts-ignore
            array.push(new L.LatLng(item.latitude, item.longitude));
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

        var center: number = Math.round(co.length/2);
        const lat = co[center].latitude;
        const lon = co[center].longitude;
        // @ts-ignore
        map.flyTo([lat, lon], 7.5)
      })
  }

  getAllCoordinates(map: L.Map,
                     py: any) {
    this.quarkusService.getAllCoordinates()
      .subscribe(c => {
        if (c != null) {
          //console.log(JSON.stringify(c));
          this.jsonArray = JSON.parse(JSON.stringify(c));
          for (const coElement of this.jsonArray) {
            this.allCoords.push({
              latitude: coElement["latitude"],
              longitude: coElement["longitude"]
            })
          }
        }
        var array: L.LatLngExpression[] | L.LatLngExpression[][] =  [];
        this.allCoords.forEach(item => {
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

        var center: number = Math.round(this.allCoords.length/2);
        const lat = this.allCoords[center].latitude;
        const lon = this.allCoords[center].longitude;
        // @ts-ignore
        map.flyTo([lat, lon], 6.2)
      })
  }
}
