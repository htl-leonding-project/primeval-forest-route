import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as L from 'leaflet';

@Injectable({
  providedIn: 'root'
})
export class MarkerService {

  constructor() { }

  public makeControlpointMarker(map: L.Map) {
    const lon = 13.659549;
    const lat = 54.571956;
    const marker = L.marker([lat, lon]);

    marker.addTo(map);
  }
}
