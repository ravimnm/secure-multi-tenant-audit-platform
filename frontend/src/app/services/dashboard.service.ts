import { Injectable, inject } from '@angular/core';

import {
  HttpClient,
  HttpHeaders
} from '@angular/common/http';

import { environment }
from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private http =
      inject(HttpClient);

  getAuditLogs() {

    const token =
      localStorage.getItem('jwt');

    return this.http.get<any>(
      `${environment.apiGatewayUrl}/audit/events`,
      {
        headers:
          new HttpHeaders({
            Authorization:
              `Bearer ${token}`
          })
      });
  }

  verifyIntegrity() {

    const token =
      localStorage.getItem('jwt');

    return this.http.get<any>(
      `${environment.apiGatewayUrl}/audit/events/verify`,
      {
        headers:
          new HttpHeaders({
            Authorization:
              `Bearer ${token}`
          })
      });
  }
}