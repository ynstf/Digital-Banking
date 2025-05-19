import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {
  BankAccountService,
  CustomerDTO,
  CreateSavingAccountRequest,
  SavingBankAccountDTO
} from '../../services/bank-account.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-create-saving-account',
  templateUrl: './create-saving-account.component.html',
})
export class CreateSavingAccountComponent implements OnInit {
  form: FormGroup;
  customerOptions: CustomerDTO[] = [];
  isSubmitting = false;

  toastMessage: string = '';
  toastType: 'success' | 'danger' | null = null;

  constructor(
    private fb: FormBuilder,
    private bankService: BankAccountService
  ) {
    this.form = this.fb.group({
      customerSearch: ['', Validators.required],
      selectedCustomerId: [null, Validators.required],
      initialBalance: [0, [Validators.required, Validators.min(0)]],
      interestRate: [0, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit(): void {
    this.form.get('customerSearch')!.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(kw => this.bankService.searchCustomers(kw))
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
    this.toastType = null;

    const req: CreateSavingAccountRequest = {
      customerId: this.form.value.selectedCustomerId,
      initialBalance: this.form.value.initialBalance,
      interestRate: this.form.value.interestRate,
    };

    this.bankService.createSavingAccount(req).subscribe({
      next: (res: SavingBankAccountDTO) => {
        this.toastType = 'success';
        this.toastMessage = `Saving account created ! ID : ${res.id}`;
        this.form.reset();
        this.customerOptions = [];
        this.isSubmitting = false;
      },
      error: (err) => {
        this.toastType = 'danger';
        this.toastMessage = `Error : Current account not created.`;
        this.isSubmitting = false;
      }
    });
  }
}
