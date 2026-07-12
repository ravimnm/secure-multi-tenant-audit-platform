import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuditService {

  private http = inject(HttpClient);

  private API =
      `${environment.apiGatewayUrl}/audit`;

  getLogs() {

    return this.http.get(
      `${this.API}/events`
    );
  }

  verifyLogs() {

    return this.http.get(
      `${this.API}/events/verify`
    );
  }

  exportLogs() {

    return this.http.get(
      `${this.API}/events/export`,
      {
        responseType: 'text'
      }
    );
  }

  getTimeline(userId: string) {

    return this.http.get(
      `${this.API}/timeline/user/${userId}`
    );
  }

  getIntegrityReport() {

    return this.http.get(
      `${this.API}/reports/integrity`
    );
  }

  getAdminReport() {

    return this.http.get(
      `${this.API}/reports/admin-activity`
    );
  }

  getDataAccessReport() {

    return this.http.get(
      `${this.API}/reports/data-access`
    );
  }
}