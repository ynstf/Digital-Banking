// src/app/dashboard/dashboard.component.ts
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
  @ViewChild('lineCanvas', { static: true }) lineCanvas!: ElementRef<HTMLCanvasElement>;  // ← new
  @ViewChild('weekdayCanvas', { static: true }) weekdayCanvas!: ElementRef<HTMLCanvasElement>;  // ← new
  @ViewChild('topCustCanvas', { static: true }) topCustCanvas!: ElementRef<HTMLCanvasElement>; // ← NEW
  @ViewChild('doughnutCanvas', { static: true }) doughnutCanvas!: ElementRef<HTMLCanvasElement>;


  accountCountByType!: Record<string, number>;
  recentOperations: any[] = [];

  constructor(private dashboardService: DashboardService) {}

  ngAfterViewInit(): void {
    this.dashboardService.getSummary().subscribe((summary: DashboardSummary) => {
      this.accountCountByType = summary.accountCountByType;
      this.recentOperations = summary.recentOperations;

      // PIE CHART (unchanged) …
      new Chart(this.pieCanvas.nativeElement.getContext('2d'), {
        type: 'pie',
        data: {
          labels: Object.keys(summary.totalBalanceByType),
          datasets: [{ data: Object.values(summary.totalBalanceByType), backgroundColor: ['#4e73df','#1cc88a','#36b9cc','#f6c23e'] }]
        },
        options: { responsive: true, plugins: { title: { display: true, text: 'Soldes par type' } } }
      });

      // BAR CHART (unchanged) …
      new Chart(this.barCanvas.nativeElement.getContext('2d'), {
        type: 'bar',
        data: {
          labels: Object.keys(summary.operationCountByType),
          datasets: [{ label: 'Opérations', data: Object.values(summary.operationCountByType), backgroundColor: '#4e73df' }]
        },
        options: { responsive: true, scales: { y: { beginAtZero: true } }, plugins: { title: { display: true, text: 'Opérations par type' } } }
      });

      // ← NEW: LINE CHART for account creations by month
      new Chart(this.lineCanvas.nativeElement.getContext('2d'), {
        type: 'line',
        data: {
          labels: Object.keys(summary.accountsCreatedByMonth),
          datasets: [{
            label: 'Comptes créés',
            data: Object.values(summary.accountsCreatedByMonth),
            borderColor: '#1cc88a',
            fill: false,
            tension: 0.3
          }]
        },
        options: {
          responsive: true,
          plugins: { title: { display: true, text: 'Créations de comptes par mois' } },
          scales: { y: { beginAtZero: true } }
        }
      });

      // ← NEW: Weekday bar chart
      new Chart(this.weekdayCanvas.nativeElement.getContext('2d'), {
        type: 'bar',
        data: {
          labels: Object.keys(summary.opsByDayOfWeek),
          datasets: [{
            label: 'Transactions',
            data: Object.values(summary.opsByDayOfWeek),
            backgroundColor: '#36b9cc'
          }]
        },
        options: {
          responsive: true,
          scales: { y: { beginAtZero: true } },
          plugins: { title: { display: true, text: 'Transactions par jour de la semaine' } }
        }
      });


      // ← NEW: Horizontal bar chart for top 5 customers
      const names = summary.topCustomers.map(c => c.name);
      const balances = summary.topCustomers.map(c => c.totalBalance);

      new Chart(this.topCustCanvas.nativeElement.getContext('2d'), {
        type: 'bar',
        data: {
          labels: names,
          datasets: [{
            label: 'Total Balance',
            data: balances,
            backgroundColor: '#f6c23e'
          }]
        },
        options: {
          indexAxis: 'y',      // ← horizontal
          responsive: true,
          scales: {
            x: { beginAtZero: true }
          },
          plugins: {
            title: { display: true, text: 'Top 5 Customers by Balance' },
            legend: { display: false }
          }
        }
      });

      // ← NEW: Doughnut chart for average balances
      new Chart(this.doughnutCanvas.nativeElement.getContext('2d'), {
        type: 'doughnut',
        data: {
          labels: Object.keys(summary.avgBalanceByType),
          datasets: [{
            data: Object.values(summary.avgBalanceByType),
            backgroundColor: ['#36b9cc', '#f6c23e']  // e.g. two types
          }]
        },
        options: {
          responsive: true,
          plugins: {
            title: { display: true, text: 'Average Balance by Account Type' },
            legend: { position: 'bottom' }
          }
        }
      });

      

      
    });

    
  }

  formatTypeName(type: string): string {
    return type.replace(/([a-z])([A-Z])/g, '$1 $2');
  }
}
