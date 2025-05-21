import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
})
export class UserListComponent implements OnInit {
  users: any[] = [];

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.getUsers().subscribe((data) => {
      this.users = data;
    });
  }
}

