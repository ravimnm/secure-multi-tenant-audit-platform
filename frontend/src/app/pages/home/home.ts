import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class HomeComponent {

  private router = inject(Router);

  darkMode = false;

  toggleTheme() {

    this.darkMode = !this.darkMode;

    document.body.classList.toggle('dark-theme');
  }

  goToLogin() {

    this.router.navigate(['/login']);
  }
}