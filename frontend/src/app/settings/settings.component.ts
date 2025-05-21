import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  usernameForm!: FormGroup;
  passwordForm!: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient,
    public authService : AuthService
  ) {}

  ngOnInit(): void {
    this.usernameForm = this.fb.group({
      newUsername: ['', Validators.required]
    });

    this.passwordForm = this.fb.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required]
    });
  }

  changeUsername() {
    if (this.usernameForm.valid) {
      this.authService.changeUsername(this.usernameForm.value);
    }
  }

  changePassword() {
    if (this.passwordForm.valid) {
      this.authService.changePassword(this.passwordForm.value);
    }
  }
}
