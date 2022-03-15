import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {RouteDto} from "./route-dto";
import {CoordinatesDto} from "./coordinates-dto";
import {PictureDto} from "./picture-dto";
import {GpxdataDto} from "./gpxdata-dto";
import {ControlPointDto} from "./controlpoint-dto";

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
    console.log(id);
    return this.http.get(`${this.baseUrl}/picture/getImageById/${id}`, {
      responseType: 'blob'
    });
  }

  getCpWithImageId(id: number): Observable<ControlPointDto> {
    return this.http.get<ControlPointDto>(`${this.baseUrl}/picture/getCpByImageId/${id}`);
  }

  getAllGpxData(): Observable<GpxdataDto[]> {
    return this.http.get<GpxdataDto[]>(
      `${this.baseUrl}/gpx/all`
    )
  }

  getAllControlPoints(): Observable<ControlPointDto[]> {
    return this.http.get<ControlPointDto[]>(
      `${this.baseUrl}/controlPoint/all`
    )
  }

  getControlPointsById(id: number | null | undefined): Observable<ControlPointDto[]> {
    return this.http.get<ControlPointDto[]>(
      `${this.baseUrl}/gpx/points/${id}`
    )
  }

  getCoordinatesById(id: number | null | undefined): Observable<CoordinatesDto[]> {
    return this.http.get<CoordinatesDto[]>(
      `${this.baseUrl}/gpx/id/${id}`
    )
  }

  uploadXml(file: File, routeName: string): Promise<GpxdataDto> {
    console.log(file);
    return this.http.post<GpxdataDto>(
      `${this.baseUrl}/gpx/uploadGPX/${routeName}`,
      file,
      {responseType: "json"}).toPromise();
  }

  uploadRoute(route: RouteDto, gpxId: number): Promise<RouteDto> {
    return this.http.post<RouteDto>(
      `${this.baseUrl}/route/uploadRouteWithGpxId/${gpxId}`,
      route,
      {responseType: "json"}).toPromise();
  }

  uploadCsv(csvFile: File, gpxId: number): Promise<string> {
    console.log(gpxId);
    console.log(csvFile);
    return this.http.post<string>(
      `${this.baseUrl}/controlPoint/import-cp-csv/${gpxId}`,
      csvFile
    ).toPromise()
  }
}
