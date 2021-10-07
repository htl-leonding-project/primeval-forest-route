import {Component, Inject, OnInit} from '@angular/core';
import {QuarkusBackendService} from "./quarkus-backend.service";
import {RouteDto} from "./route-dto";
import {HttpClient} from "@angular/common/http";
import {readAndParseJson} from "@angular/cli/utilities/json-file";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
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
    /*this.http.get<any>('localhost:8080/route/all').subscribe(res => {
      this.routes.concat(res.body);
    })*/
  }


}
