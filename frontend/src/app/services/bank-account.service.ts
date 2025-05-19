import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface CustomerDTO {
  id: number;
  name: string;
  email: string;
}

export interface CreateCurrentAccountRequest {
  initialBalance: number;
  overDraft: number;
  customerId: number;
}

export interface CurrentBankAccountDTO {
  id: string;
  balance: number;
  createdAt: string;
  overDraft: number;
  status: string;
  customerId: number;
}

@Injectable({ providedIn: 'root' })
export class BankAccountService {


  constructor(private http: HttpClient) {}

  listCustomers(): Observable<CustomerDTO[]> {
    return this.http.get<CustomerDTO[]>(`${environment.backendHost}/customers`);
  }

  searchCustomers(keyword: string): Observable<CustomerDTO[]> {
    return this.http.get<CustomerDTO[]>(
      `${environment.backendHost}/customers/search?keyword=${encodeURIComponent(keyword)}`
    );
  }

  createCurrentAccount(req: CreateCurrentAccountRequest): Observable<CurrentBankAccountDTO> {
    return this.http.post<CurrentBankAccountDTO>(
      `${environment.backendHost}/accounts/current`,
      req
    );
  }
}
