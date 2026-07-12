import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar {

  private router = inject(Router);

  goToLogin() {

    this.router.navigate(['/login']);
  }

  logout() {

    localStorage.removeItem('jwt');

    this.router.navigate(['/']);
  }
  get isLoggedIn(): boolean {

    return localStorage.getItem('jwt') != null;
  }

  darkMode = false;

  toggleTheme() {

      this.darkMode = !this.darkMode;

      document.body.classList.toggle('dark-theme');
  }

}