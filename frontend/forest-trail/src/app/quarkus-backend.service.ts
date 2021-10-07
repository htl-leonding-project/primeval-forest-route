import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HttpClientModule} from "@angular/common/http";
import {Observable} from "rxjs";
import {RouteDto} from "./route-dto";

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
}
