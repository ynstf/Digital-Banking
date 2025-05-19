// src/app/pages/create-current-account/create-current-account.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  BankAccountService,
  CustomerDTO,
  CreateCurrentAccountRequest,
  CurrentBankAccountDTO
} from '../../services/bank-account.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { timer } from 'rxjs';

@Component({
  selector: 'app-create-current-account',
  templateUrl: './create-current-account.component.html',
})
export class CreateCurrentAccountComponent implements OnInit {
  form: FormGroup;
  customerOptions: CustomerDTO[] = [];
  isSubmitting = false;
  errorMessage: string | null = null;
  toastType: 'success' | 'error' | null = null;
  toastMessage = '';

  constructor(
    private fb: FormBuilder,
    private bankService: BankAccountService
  ) {
    this.form = this.fb.group({
      customerSearch: ['', Validators.required],
      selectedCustomerId: [null, Validators.required],
      initialBalance: [0, [Validators.required, Validators.min(0)]],
      overDraft: [0, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit(): void {
    this.form.get('customerSearch')!.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((kw: string) => this.bankService.searchCustomers(kw))
    ).subscribe(list => this.customerOptions = list);
  }

  onSelectCustomer(c: CustomerDTO) {
    this.form.patchValue({
      customerSearch: c.name,
      selectedCustomerId: c.id
    });
    this.customerOptions = [];
  }

  onSubmit() {
    if (this.form.invalid) return;

    this.isSubmitting = true;
    this.errorMessage = null;
    this.toastType = null;
    this.toastMessage = '';

    const req: CreateCurrentAccountRequest = {
      customerId: this.form.value.selectedCustomerId,
      initialBalance: this.form.value.initialBalance,
      overDraft: this.form.value.overDraft,
    };

    this.bankService.createCurrentAccount(req).subscribe({
      next: (res: CurrentBankAccountDTO) => {
        const id = res.id;  // <— use `id`, not `accountId`
        this.toastType = 'success';
        this.toastMessage = `Current account created ! ID : ${id}`;
        this.showToastAndReset();
      },
      error: err => {
        const msg = err.error?.message || 'Error : Current account not created.';
        this.errorMessage = msg;
        this.toastType = 'error';
        this.toastMessage = msg;
        this.showToastAndReset();
      }
    });
  }

  private showToastAndReset() {
    this.isSubmitting = false;
    if (this.toastType === 'success') {
      this.form.reset();
      this.customerOptions = [];
    }
    timer(4000).subscribe(() => this.toastType = null);
  }
}
