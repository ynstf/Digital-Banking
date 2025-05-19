// src/app/edit-customer/edit-customer.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../services/customer.service';
import { Customer } from '../model/customer.model';

@Component({
  selector: 'app-edit-customer',
  templateUrl: './edit-customer.component.html',
  styleUrls: ['./edit-customer.component.css']
})
export class EditCustomerComponent implements OnInit {
  customerForm!: FormGroup;
  customerId!: number;
  errorMessage!: string;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    public router: Router,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    // 1. Récupérer l’ID depuis l’URL
    this.customerId = +this.route.snapshot.paramMap.get('id')!;
    // 2. Initialiser le formulaire
    this.customerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
    // 3. Charger les données existantes
    this.customerService.getCustomer(this.customerId).subscribe({
      next: (cust) => {
        this.customerForm.patchValue({
          name: cust.name,
          email: cust.email
        });
      },
      error: (err) => this.errorMessage = err.message
    });
  }

  handleSaveCustomer(): void {
    if (this.customerForm.invalid) return;
    const updated: Customer = {
      id: this.customerId,
      name: this.customerForm.value.name,
      email: this.customerForm.value.email
    };
    this.customerService.updateCustomer(this.customerId, updated)
      .subscribe({
        next: () => this.router.navigateByUrl('/admin/customers'),
        error: err => this.errorMessage = err.message
      });
      
  }
}
