// src/app/dashboard/dashboard.component.ts

import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
// 👇 be sure this points at the service file above
import { DashboardService } from '../services/dashboard.service';

declare const Chart: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements AfterViewInit {
  @ViewChild('pieCanvas', { static: true }) pieCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('barCanvas', { static: true }) barCanvas!: ElementRef<HTMLCanvasElement>;

  constructor(private dashboardService: DashboardService) { }  // Angular needs the class here

  ngAfterViewInit(): void {
    this.dashboardService.getSummary().subscribe(summary => {
      // PIE CHART
      new Chart(this.pieCanvas.nativeElement.getContext('2d'), {
        type: 'pie',
        data: {
          labels: Object.keys(summary.totalBalanceByType),
          datasets: [{
            data: Object.values(summary.totalBalanceByType),
            backgroundColor: ['#4e73df','#1cc88a','#36b9cc','#f6c23e','#e74a3b']
          }]
        },
        options: {
          responsive: true,
          plugins: {
            title: { display: true, text: 'Soldes par type de compte' }
          }
        }
      });

      // BAR CHART
      new Chart(this.barCanvas.nativeElement.getContext('2d'), {
        type: 'bar',
        data: {
          labels: Object.keys(summary.operationCountByType),
          datasets: [{
            label: 'Nombre d’opérations',
            data: Object.values(summary.operationCountByType),
            backgroundColor: '#4e73df'
          }]
        },
        options: {
          responsive: true,
          scales: {
            y: { beginAtZero: true }
          },
          plugins: {
            title: { display: true, text: 'Opérations par type' }
          }
        }
      });
    });
  }
}
