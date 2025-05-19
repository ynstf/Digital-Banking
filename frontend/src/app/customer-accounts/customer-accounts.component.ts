import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from '../model/customer.model';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AccountDetails } from '../model/account.model';
import { AccountsService } from '../services/accounts.service';
import { AccountDTO } from '../model/account.dto';

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {
  customerId!: string;
  customer!: Customer;
  // Liste des comptes
  //accounts$!: Observable<{ id: string; type: string; balance: number }[]>;
  accounts$!: Observable<AccountDTO[]>;
  // Détails du compte sélectionné (solde + opérations)
  accountDetails?: AccountDetails;
  selectedAccountId?: string;
  errorMessage!: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountsService
  ) {
    const nav = this.router.getCurrentNavigation();
    if (nav?.extras?.state) {
      this.customer = nav.extras.state as Customer;
    }
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.paramMap.get('id')!;
    this.loadAccounts();
  }



  onSelectAccount(accountId: string) {
    this.selectedAccountId = accountId;
    this.loadAccountDetails(accountId, 0);
  }

  

  loadAccounts(): void {
    this.accounts$ = this.accountService
      .getAccountsByCustomer(this.customerId)
      .pipe(
        catchError(err => {
          this.errorMessage = `Erreur chargement comptes : ${err.message}`;
          return of([]);
        })
      );
  }

  loadAccountDetails(accountId: string, page: number) {
    this.accountService.getAccountDetails(accountId, page)
      .pipe(
        catchError(err => {
          this.errorMessage = `Erreur chargement opérations : ${err.message}`;
          return of(undefined);
        })
      )
      .subscribe(details => this.accountDetails = details);
  }

  onPageChange(page: number) {
    if (this.selectedAccountId) {
      this.loadAccountDetails(this.selectedAccountId, page);
    }
  }

  onDeleteAccount(accountId: string) {
    if (confirm('Voulez-vous vraiment supprimer ce compte ?')) {
      this.accountService.deleteAccount(accountId).subscribe({
        next: () => this.loadAccounts(),
        error: (err) => alert('Erreur lors de la suppression : ' + err.message),
      });
    }
  }

}
