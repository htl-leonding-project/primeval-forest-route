import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GpxdataDto} from "../gpxdata-dto";
import {QuarkusBackendService} from "../quarkus-backend.service";
import {ControlPointDto} from "../controlpoint-dto";
import {CoordinatesDto} from "../coordinates-dto";
import {MapComponent} from "../map/map.component";

@Component({
  selector: 'app-route-menu',
  templateUrl: './route-menu.component.html',
  styleUrls: ['./route-menu.component.css']
})
export class RouteMenuComponent implements OnInit {

  controlpoints: ControlPointDto[] = [];
  coordinates: CoordinatesDto[] = [];
  routes: GpxdataDto[] = [];
  selectedRoute: GpxdataDto = {};
  jsonArray: [] = [];
  @Output()
  emitter: EventEmitter<Number> = new EventEmitter<Number>();

  constructor(private quarkusService: QuarkusBackendService,
              private readonly mapComponent: MapComponent) { }

  ngOnInit(): void {
    this.getGpxData();
  }

  getGpxData() {
    this.quarkusService.getAllGpxData()
      .subscribe(r => {
        if (r != null) {
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const gpxElement of this.jsonArray) {
            this.routes.push({
              id: gpxElement["id"],
              name: gpxElement["name"]
            })
          }
        }
      })
  }

  getControlPointsById(id: number | null | undefined) {
    this.quarkusService.getControlPointsById(id)
      .subscribe(r => {
        if (r != null) {
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const gpxElement of this.jsonArray) {
            this.controlpoints.push({
              latitudeCoordinate: gpxElement["latitude"],
              longitudeCoordinate: gpxElement["longitude"]
            })
          }
        }
      })
  }

  changesOfSelection() {
    this.mapComponent.changeMapView(this.selectedRoute);
  }
}
