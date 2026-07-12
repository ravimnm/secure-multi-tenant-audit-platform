import {
  Component,ChangeDetectorRef,
  OnInit,NgZone,
  inject
} from '@angular/core';
// import {ChangeDetectorRef,NgZone
// } from '@angular/core';

import {
  CommonModule,
  NgClass
} from '@angular/common';

import {
  BaseChartDirective
} from 'ng2-charts';

import { Navbar }
from '../../components/navbar/navbar';

import { Sidebar }
from '../../components/sidebar/sidebar';

import { AuditService }
from '../../services/audit.service';

import { SecurityService }
from '../../services/security.service';

@Component({
  selector: 'app-dashboard',

  standalone: true,

  imports: [
    CommonModule,
    NgClass,
    Navbar,
    Sidebar,
    BaseChartDirective
  ],

  templateUrl: './dashboard.html',

  styleUrl: './dashboard.css'
})
export class Dashboard
implements OnInit {

  private auditService =
      inject(AuditService);
      
  private securityService =
      inject(SecurityService);

  private zone = inject(NgZone);
  private cdr = inject(ChangeDetectorRef);
  // private cdr = inject(ChangeDetectorRef);
  selectedRange = 'today';

  // KPI Cards

  totalLogs = 0;

  securityEvents = 0;

  verified = 0;

  logTrend = '';

  securityTrend = '';

  integrityTrend = '';

  timelineEvents:any[]=[];

  openAlerts=0;

  criticalAlerts=0;

  highRiskUsers=0;

  activeTenants=4;

  // Charts

  // doughnutData = {

  //   labels: ['Verified'],

  //   datasets: [{
  //     data: [0]
  //   }]
  // };

  // monthlyChart = {

  //   labels: [],

  //   datasets: [{
  //     label: 'Audit Logs',
  //     data: []
  //   }]
  // };

  // severityChart = {

  //   labels: ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'],

  //   datasets: [{
  //     data: [0, 0, 0, 0]
  //   }]
  // };
  doughnutData = {
    labels: ['Verified'],
    datasets: [{
      data: [0]
    }]
  };

  monthlyChart = {
    labels: [] as string[],
    datasets: [{
      label: 'Audit Logs',
      data: [] as number[]
    }]
  };

  severityChart = {
    labels: ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'],
    datasets: [{
      data: [0, 0, 0, 0]
    }]
  };

  recentAudits: any[] = [];

  ngOnInit(): void {

    this.loadDashboard();
    this.loadTimeline();
    this.loadSeverityChart();

  }

  changeRange(range: string) {

    this.selectedRange = range;

    this.loadDashboard();
  }
  loadTimeline() {

    this.auditService
        .getTimeline('agent')
        .subscribe({

          next:(response:any)=>{

              console.log("TIMELINE",response);

              this.timelineEvents=response;
          },
          

          error:err=>console.log(err)
        });
  }
  loadSeverityChart() {

    this.securityService
        .getEvents()
        .subscribe({

          next: (response: any) => {

            const events =
                response.data.content || [];

            const low =
                events.filter(
                  (e: any) =>
                      e.severity === 'LOW'
                ).length;

            const medium =
                events.filter(
                  (e: any) =>
                      e.severity === 'MEDIUM'
                ).length;

            const high =
                events.filter(
                  (e: any) =>
                      e.severity === 'HIGH'
                ).length;

            const critical =
                events.filter(
                  (e: any) =>
                      e.severity === 'CRITICAL'
                ).length;

            this.severityChart.labels = [
              'LOW',
              'MEDIUM',
              'HIGH',
              'CRITICAL'
            ];

            this.severityChart.datasets[0].data = [
              low,
              medium,
              high,
              critical
            ];

            this.securityEvents = events.length;

            setTimeout(() => {
              this.cdr.detectChanges();
            }, 0);
          },

          error: err => console.error(err)

        });
  }
  // loadSeverityChart(){

  //   this.securityService
  //       .getEvents()
  //       .subscribe({

  //         next:(response:any)=>{

  //           const events =
  //               response.data.content || [];

  //           const low =
  //               events.filter(
  //                   (e:any)=>
  //                       e.severity==='LOW').length;

  //           const medium =
  //               events.filter(
  //                   (e:any)=>
  //                       e.severity==='MEDIUM').length;

  //           const high =
  //               events.filter(
  //                   (e:any)=>
  //                       e.severity==='HIGH').length;

  //           const critical =
  //               events.filter(
  //                   (e:any)=>
  //                       e.severity==='CRITICAL').length;

  //           this.severityChart = {

  //             labels:[
  //                 'LOW',
  //                 'MEDIUM',
  //                 'HIGH',
  //                 'CRITICAL'
  //             ],

  //             datasets:[{
  //               data:[
  //                   low,
  //                   medium,
  //                   high,
  //                   critical
  //               ]
  //             }]
  //           };

  //           this.securityEvents =
  //               events.length;
  //         }

  //       });
  // }

  loadDashboard() {

    this.auditService
        .getLogs()
        .subscribe({

          next: (response: any) => {

            const logs = response?.data?.data || [];

            this.zone.run(() => {

              this.totalLogs = logs.length;

              this.verified =
                  logs.length > 0 ? 100 : 0;

              this.recentAudits =
                  logs.slice(0, 5);

              // Update doughnut chart

              this.doughnutData.labels = ['Verified'];

              this.doughnutData.datasets[0].data = [
                logs.length
              ];

              // Update line/bar chart

              this.monthlyChart.labels =
                  logs.map(
                    (_: any, i: number) =>
                        `Log ${i + 1}`
                  );

              this.monthlyChart.datasets[0].data =
                  logs.map(() => 1);

              console.log("TOTAL =", this.totalLogs);

              setTimeout(() => {
                this.cdr.detectChanges();
              }, 0);

            });

          },

          error: err => console.error(err)

        });
  }

  // loadDashboard() {

  //   this.auditService
  //       .getLogs()
  //       .subscribe({

  //         next: (response:any) => {

  //           console.log(response);

  //           const logs = response?.data?.data || [];

  //           this.zone.run(() => {

  //             this.totalLogs = logs.length;

  //             this.verified =
  //               logs.length > 0 ? 100 : 0;

  //             this.recentAudits =
  //               logs.slice(0,5);

  //             this.doughnutData = {
  //               labels:['Verified'],
  //               datasets:[{
  //                 data:[logs.length]
  //               }]
  //             };

  //             this.monthlyChart = {
  //               labels: logs.map(
  //                 (log:any,i:number)=>'Log '+(i+1)
  //               ),

  //               datasets:[{
  //                 label:'Audit Logs',
  //                 data: logs.map(()=>1)
  //               }]
  //             };

  //             console.log("TOTAL =", this.totalLogs);

  //             //this.cdr.detectChanges();

  //           });

  //         },

  //         error: err => console.error(err)

  //       });
  // }

  // loadDashboard() {

  //   this.auditService
  //       .getLogs()
  //       .subscribe({

  //         next: (response:any) => {

  //           console.log(response);

  //           const logs = response?.data?.data || [];

  //           this.totalLogs = logs.length;

  //           this.verified =
  //               logs.length > 0 ? 100 : 0;

  //           this.recentAudits =
  //               logs.slice(0,5);

  //           this.doughnutData = {
  //             labels:['Verified'],
  //             datasets:[{
  //               data:[logs.length]
  //             }]
  //           };

  //           this.monthlyChart = {

  //             labels: logs.map(
  //               (log:any,i:number) =>
  //                   'Log ' + (i + 1)
  //             ),

  //             datasets:[{
  //               label:'Audit Logs',
  //               data: logs.map(()=>1)
  //             }]
  //           };

  //         },

  //         error: err => console.error(err)

  //       });
  // }
}
