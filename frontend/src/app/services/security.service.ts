import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private http = inject(HttpClient);

  private API =
      `${environment.apiGatewayUrl}/security`;

  getEvents() {

    return this.http.get(
      `${this.API}/events`
    );
  }

  getAlerts() {

    return this.http.get(
      `${this.API}/alerts`
    );
  }

  getOpenAlerts() {

    return this.http.get(
      `${this.API}/alerts/open`
    );
  }

  resolveAlert(id: number) {

    return this.http.patch(
      `${this.API}/alerts/${id}/resolve`,
      {}
    );
  }

}