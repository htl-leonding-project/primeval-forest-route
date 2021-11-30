import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HttpClientModule} from "@angular/common/http";
import {Observable} from "rxjs";
import {RouteDto} from "./route-dto";
import {CoordinatesDto} from "./coordinates-dto";
import {ControlPointDto} from "./controlpoint-dto";

@Injectable({
  providedIn: 'root'
})
export class QuarkusBackendService {

  constructor(private http: HttpClient) { }

  getAllRoutes(): Observable<RouteDto[]> {
    return this.http.get<RouteDto[]>(
      "http://localhost:8080/api/route/all"
    );
  }

  getAllCoordinates(): Observable<CoordinatesDto[]> {
    return this.http.get<CoordinatesDto[]>(
      "http://localhost:8080/api/coordinates/all"
    )
  }

  getAllControlPoints(): Observable<ControlPointDto[]> {
    return this.http.get<ControlPointDto[]>(
      "http://localhost:8080/api/controlPoint/all"
    )
  }
}
