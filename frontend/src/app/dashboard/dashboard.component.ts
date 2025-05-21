import { Component, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { DashboardService, DashboardSummary } from '../services/dashboard.service';

declare const Chart: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements AfterViewInit {
  @ViewChild('pieCanvas', { static: true }) pieCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('barCanvas', { static: true }) barCanvas!: ElementRef<HTMLCanvasElement>;

  accountCountByType!: Record<string, number>;

  constructor(private dashboardService: DashboardService) {}

  ngAfterViewInit(): void {
    this.dashboardService.getSummary().subscribe((summary: DashboardSummary) => {
      this.accountCountByType = summary.accountCountByType;

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

  /** 
   * Inserts a space between lowercase-uppercase pairs:
   *   "SavingAccount" → "Saving Account"
   */
  formatTypeName(type: string): string {
    return type.replace(/([a-z])([A-Z])/g, '$1 $2');
  }
}
