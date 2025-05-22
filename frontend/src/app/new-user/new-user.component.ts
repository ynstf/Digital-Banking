// ✅ 1. new-user.component.ts
import { Component } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { NewUser } from '../model/user.model';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css']
})
export class NewUserComponent {
  userForm: FormGroup;
  rolesList = ['USER', 'ADMIN'];
  errorMessage = '';


  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      roles: this.buildRolesFormArray(),
    });
  }

  buildRolesFormArray(): FormArray {
    const controls = this.rolesList.map(() => new FormControl(false));
    return this.fb.array(controls, this.minSelectedRoles(1));
  }

  minSelectedRoles(min: number) {
    return (formArray: AbstractControl): { [key: string]: boolean } | null => {
      const selected = (formArray as FormArray).controls
        .map(c => c.value)
        .filter(v => v === true);
      return selected.length >= min ? null : { required: true };
    };
  }

  get rolesFormArray() {
    return this.userForm.get('roles') as FormArray;
  }

  // onSubmit() {
  //   if (this.userForm.invalid) return;

  //   const selectedRoles = this.userForm.value.roles
  //     .map((v: boolean, i: number) => (v ? this.rolesList[i] : null))
  //     .filter((v: string | null) => v !== null);

  //   const payload = {
  //     username: this.userForm.value.username,
  //     password: this.userForm.value.password,
  //     roles: selectedRoles,
  //   };

  //   this.authService.createUser(payload).subscribe({
  //     next: () => this.router.navigate(['/users']),
  //     error: (err) => {
  //       if (err.error?.message?.includes('User already exists')) {
  //         this.errorMessage = 'Username already exists.';
  //       } else {
  //         this.errorMessage = 'An error occurred while creating the user.';
  //       }
  //     },
  //   });
  // }

  onSubmit(): void {
  if (this.userForm.invalid) {
    this.userForm.markAllAsTouched(); // highlight errors
    return;
  }


  const selectedRoles = this.userForm.value.roles
  .map((selected: boolean, i: number) => selected ? this.rolesList[i] : null)
  .filter((role: string | null): role is string => role !== null);


  const newUser: NewUser = {
    username: this.userForm.value.username,
    password: this.userForm.value.password,
    roles: selectedRoles
  };

  this.authService.createUser(newUser).subscribe({
    next: (data) => {
      // Only navigate if creation is successful
      this.router.navigate(['/admin/users']);
    },
    error: (err) => {
      console.log(err);
      // Handle only the "already exists" error
      if (err.error?.message?.includes("User already exists")) {
        this.errorMessage = "User already exists.";
      } else {
        // Optional: show generic error or keep silent
        this.errorMessage = "User already exists.";
        console.error(err);
      }
    }
  });

}

}
