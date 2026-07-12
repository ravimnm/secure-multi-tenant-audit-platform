import { Routes } from '@angular/router';

import { Login } from './pages/login/login';
import { HomeComponent } from './pages/home/home';
import { Dashboard } from './pages/dashboard/dashboard';
import { AuditList } from './modules/audit/audit-list/audit-list';
import { SecurityList } from './modules/security/security-list/security-list';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [

  {
    path: '',
    component: HomeComponent
  },

  {
    path: 'login',
    component: Login
  },

  {
    path: 'dashboard',
    component: Dashboard,
    canActivate: [authGuard]


  },

  {
    path: 'audit',
    component: AuditList,
    canActivate: [authGuard]
  },

  {
    path: 'security',
    component: SecurityList,
    canActivate: [authGuard]
  },

  {
    path: '**',
    redirectTo: ''
  }
];