import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  private authService = inject(AuthService);
  private router = inject(Router);


  login(
    username: string,
    password: string
  ) {

    this.authService
        .login(username, password)

        .subscribe({

          next: token => {

            this.authService
                .saveToken(token);

            this.router.navigate(
                ['/dashboard']);
          },

          error: err => {

            alert(
              'Invalid username or password');
          }
        });
  }
}