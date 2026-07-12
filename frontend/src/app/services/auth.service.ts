import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);

  private API =
      `${environment.apiGatewayUrl}/auth`;

  login(
    username: string,
    password: string
  ) {

    return this.http.post(
      `${this.API}/login`,
      {
        username,
        password
      },
      {
        responseType: 'text'
      }
    );
  }

  saveToken(token: string) {

    localStorage.setItem(
      'jwt',
      token);
  }

  getToken() {

    return localStorage.getItem('jwt');
  }

  logout() {

    localStorage.clear();
  }
  getTimeline(userId:string){

    return this.http.get(
      `${environment.apiGatewayUrl}/audit/timeline/user/${userId}`
    );
  }
}