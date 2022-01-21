import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {RouteDto} from "./route-dto";
import {CoordinatesDto} from "./coordinates-dto";
import {PictureDto} from "./picture-dto";

@Injectable({
  providedIn: 'root'
})
export class QuarkusBackendService {

  private baseUrl = "http://localhost:8080/api";

  constructor(private http: HttpClient) { }

  getAllRoutes(): Observable<RouteDto[]> {
    return this.http.get<RouteDto[]>(
      `${this.baseUrl}/route/all`
    );
  }

  getAllCoordinates(): Observable<CoordinatesDto[]> {
    return this.http.get<CoordinatesDto[]>(
      `${this.baseUrl}/coordinates/all`
    )
  }

  uploadImage(file: File): Observable<PictureDto> {
    const formData: FormData = new FormData();

    formData.append("value", file);
    formData.append("name", file.name);
    console.log(formData.get("name"));
    console.log(formData.get("value"));

    return this.http.post<PictureDto>(
      `${this.baseUrl}/picture/uploadPicture`,
      formData,
      {responseType: "json"});
  }

  getImage(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/picture/getImageById/${id}`);
  }
}
