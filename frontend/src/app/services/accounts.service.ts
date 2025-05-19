import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/account.model";
import { AccountDTO } from '../model/account.dto';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http : HttpClient) { }

  public getAccount(accountId : string, page : number, size : number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(environment.backendHost+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size);
  }
  public debit(accountId : string, amount : number, description:string){
    let data={accountId : accountId, amount : amount, description : description}
    return this.http.post(environment.backendHost+"/accounts/debit",data);
  }
  public credit(accountId : string, amount : number, description:string){
    let data={accountId : accountId, amount : amount, description : description}
    return this.http.post(environment.backendHost+"/accounts/credit",data);
  }
  public transfer(accountSource: string,accountDestination: string, amount : number, description:string){
    let data={accountSource, accountDestination, amount, description }
    return this.http.post(environment.backendHost+"/accounts/transfer",data);
  }

  deleteAccount(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.backendHost}/accounts/${id}`);
  }



  

  // Récupère les comptes d’un client
  getAccountsByCustomer(customerId: string): Observable<AccountDTO[]> {
  return this.http.get<AccountDTO[]>(
      `${environment.backendHost}/customers/${customerId}/accounts`
  );
}

  // Récupère la page d’opérations d’un compte
  getAccountDetails(
    accountId: string,
    page: number = 0,
    size: number = 5
  ): Observable<AccountDetails> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<AccountDetails>(
      `${environment.backendHost}/accounts/${accountId}/pageOperations`,
      { params }
    );
  }




}
