import {Component, Inject, OnInit} from '@angular/core';
import {QuarkusBackendService} from "./quarkus-backend.service";
import {RouteDto} from "./route-dto";
import {HttpClient} from "@angular/common/http";
import {readAndParseJson} from "@angular/cli/utilities/json-file";

declare var ol: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  latitude: number = 0;
  longitude: number = 0;

  map: any;

  title = 'forest-trail';
  routes: RouteDto[] = [];
  jsonArray: [] = [];

  constructor(public quarkusService: QuarkusBackendService,
              private http: HttpClient) {  }

  ngOnInit(): void {

    this.quarkusService.getAllRoutes()
      .subscribe(r => {
        if (r != null) {
          console.log(JSON.stringify(r));
          this.jsonArray = JSON.parse(JSON.stringify(r));
          for (const routeElement of this.jsonArray) {
            this.routes.push({
              id: routeElement["id"],
              csvId: routeElement["csvId"],
              name: routeElement["name"],
              length: routeElement["length"]
            })
          }
          console.log(this.routes);
        }
      })

    this.map = new ol.Map({
      target: 'map',
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM()
        })
      ],
      view: new ol.View({
        center: ol.proj.fromLonLat([73.8567, 18.5204]),
        zoom: 8
      })
      /*this.http.get<any>('localhost:8080/route/all').subscribe(res => {
        this.routes.concat(res.body);
      })*/
    })
  }


}
