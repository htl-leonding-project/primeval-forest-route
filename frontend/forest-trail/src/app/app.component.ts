import {Component, Inject, Input, OnInit} from '@angular/core';
import {QuarkusBackendService} from "./quarkus-backend.service";
import {RouteDto} from "./route-dto";
import {HttpClient} from "@angular/common/http";
import {CoordinatesDto} from "./coordinates-dto";
import {GpxdataDto} from "./gpxdata-dto";
import * as L from "leaflet";
import {ControlPointDto} from "./controlpoint-dto";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  latitude: number = 0;
  longitude: number = 0;

  title = 'forest-trail';
  routes: RouteDto[] = [];
  coordinates: CoordinatesDto[] = [];
  coordinat: GpxdataDto[] = [];
  controlpoints: ControlPointDto[] = [];
  jsonArray: [] = [];

  constructor(public quarkusService: QuarkusBackendService,
              private http: HttpClient) {  }

  ngOnInit(): void {

    //this.getRoutes();
    //this.getCoordinates();
    //this.getGpxData();
    //this.getControlPoints();

    // console.log(this.controlpoints);
    // console.log(this.coordinates);
    // console.log(this.routes);
    // console.log(this.coordinat);
  }

  // getControlPoints() {
  //   this.quarkusService.getAllControlPoints()
  //     .subscribe(r => {
  //       if (r != null) {
  //         //console.log(JSON.stringify(r));
  //         this.jsonArray = JSON.parse(JSON.stringify(r));
  //         for (const cpElement of this.jsonArray) {
  //           this.controlpoints.push({
  //             id: cpElement["id"],
  //             latitude: cpElement["latitudeCoordinate"],
  //             longitude: cpElement["longitudeCoordinate"],
  //             name: cpElement["name"]
  //           })
  //         }
  //       }
  //     })
  // }

  getRoutes() {
    this.quarkusService.getAllRoutes()
      .subscribe(r => {
        if (r != null) {
          //console.log(JSON.stringify(r));
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const routeElement of this.jsonArray) {
            this.routes.push({
              id: routeElement["id"],
              csvId: routeElement["csvId"],
              name: routeElement["name"],
              length: routeElement["length"]
            })
          }
        }
      })
  }

  getCoordinates() {
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
        //console.log(this.coordinates[0].longitude);
        //console.log(this.coordinates[0].latitude);
      })
  }

  getGpxData() {
    this.quarkusService.getAllGpxData()
      .subscribe(r => {
        if (r != null) {
          //console.log(JSON.stringify(r));
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const gpxElement of this.jsonArray) {
            this.coordinat.push({
              id: gpxElement["id"],
              name: gpxElement["name"],
            })
          }
        }
      })
  }
}


