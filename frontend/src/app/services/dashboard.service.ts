import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface TopCustomer {
  name: string;
  totalBalance: number;
}

export interface DashboardSummary {
  customerCount: number;
  totalAccountCount: number;
  accountCountByType: Record<string, number>;
  totalBalanceByType: Record<string, number>;
  operationCountByType: Record<string, number>;
  accountsCreatedByMonth: Record<string, number>;
  opsByDayOfWeek: Record<string, number>;
  topCustomers: TopCustomer[];
  avgBalanceByType: Record<string, number>;
  
  recentOperations:[];

  
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
  
  root : String = environment.backendHost ;

  constructor(private http: HttpClient) {}

  getSummary(): Observable<DashboardSummary> {
    return this.http.get<DashboardSummary>(this.root+'/dashboard/summary');
  }
}
