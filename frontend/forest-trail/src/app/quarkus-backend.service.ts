import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {HttpClientModule} from "@angular/common/http";
import {Observable} from "rxjs";
import {RouteDto} from "./route-dto";
import {CoordinatesDto} from "./coordinates-dto";

@Injectable({
  providedIn: 'root'
})
export class QuarkusBackendService {

  private baseUrl = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getAllRoutes(): Observable<RouteDto[]> {
    return this.http.get<RouteDto[]>(
      `${this.baseUrl}/api/route/all`
    );
  }

  getAllCoordinates(): Observable<CoordinatesDto[]> {
    return this.http.get<CoordinatesDto[]>(
      `${this.baseUrl}/api/coordinates/all`
    )
  }

  uploadFile(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append("file", file);

    const req = new HttpRequest(
      "POST",
      `${this.baseUrl}/uploadImage`,
      formData,
      {
        responseType: "json"
      })

    return this.http.request(req);
  }
}
