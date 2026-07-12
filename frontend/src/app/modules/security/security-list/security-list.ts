import {
  Component,
  OnInit,
  inject
} from '@angular/core';
import {
  ChangeDetectorRef
} from '@angular/core';
import { CommonModule }
from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { Navbar }
from '../../../components/navbar/navbar';

import { Sidebar }
from '../../../components/sidebar/sidebar';

import { SecurityService }
from '../../../services/security.service';

@Component({

  selector: 'app-security-list',

  standalone: true,

  imports: [
    BaseChartDirective,
    CommonModule,
    Navbar,
    Sidebar
  ],

  templateUrl: './security-list.html',

  styleUrl: './security-list.css'
})
export class SecurityList
implements OnInit {

  private securityService =
      inject(SecurityService);

  events: any[] = [];
  alerts:any[]=[];
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {

    this.loadEvents();

    this.loadAlerts();
  }
  loadAlerts() {

    this.securityService
        .getAlerts()
        .subscribe({

          next:(response:any)=>{

            this.alerts=response;
            
            this.cdr.detectChanges();
          },

          error:err=>console.log(err)
        });
  }
  

  loadEvents() {

    this.securityService
        .getEvents()

        .subscribe({

          next: (response: any) => {

              console.log(response);

              this.events = response.data.content;

              console.log(this.events);
          },

          error: (err) => {

            console.error(err);
          }
        });
  }

  severityClass(
      severity: string) {

    switch(severity) {

      case 'CRITICAL':
        return 'badge bg-danger';

      case 'HIGH':
        return 'badge bg-warning';

      case 'MEDIUM':
        return 'badge bg-primary';

      default:
        return 'badge bg-secondary';
    }
  }
  riskChart = {

    labels: ['ravi','john','alice','system'],

    datasets: [
      {
        data: [90,60,30,10],
        label: 'Risk Score'
      }
    ]
  };
}