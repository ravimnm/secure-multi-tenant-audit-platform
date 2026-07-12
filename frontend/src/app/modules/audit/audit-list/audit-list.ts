import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Navbar }
from '../../../components/navbar/navbar';

import { Sidebar }
from '../../../components/sidebar/sidebar';

import { AuditService }
from '../../../services/audit.service';

@Component({
  selector: 'app-audit-list',

  standalone: true,

  imports: [
    CommonModule,
    FormsModule,
    Navbar,
    Sidebar
  ],

  templateUrl: './audit-list.html',

  styleUrl: './audit-list.css'
})
export class AuditList implements OnInit {

  private auditService = inject(AuditService);

  logs: any[] = [];
  filteredLogs: any[] = [];

  private cdr = inject(ChangeDetectorRef);
  actor: string = '';
  action: string = '';

  ngOnInit(): void {

    console.log('AUDIT COMPONENT CREATED');

    this.loadLogs();
  }

  loadLogs() {

    this.auditService
        .getLogs()
        .subscribe({

          next: (response: any) => {

            console.log(response);

            this.logs = response?.data?.data || [];

            this.filteredLogs = [...this.logs];

            console.log('Logs array:', this.logs);
            console.log('Filtered:', this.filteredLogs);

            this.cdr.detectChanges();
          },

          error: err => {

            console.error(err);
          }
        });
  }

  searchLogs(): void {

    console.log('SEARCH CALLED');
    console.log('Actor:', this.actor);
    console.log('Action:', this.action);
    console.log('Logs before filter:', this.logs);

    if (!this.actor.trim() && !this.action.trim()) {

      this.filteredLogs = [...this.logs];

      console.log('Returning all logs');
      console.log(this.filteredLogs);

      return;
    }

    this.filteredLogs = this.logs.filter(log => {

      const actorMatch =
        !this.actor.trim() ||
        log.actorId?.toLowerCase()
            .includes(this.actor.toLowerCase());

      const actionMatch =
        !this.action.trim() ||
        log.action?.toLowerCase()
            .includes(this.action.toLowerCase());

      return actorMatch && actionMatch;
    });

    console.log('After filtering:', this.filteredLogs);
  }

  clearSearch(): void {
    console.log('CLEAR CALLED');

    this.actor = '';
    this.action = '';

    this.filteredLogs = [...this.logs];
  }
}