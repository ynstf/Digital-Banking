import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin! : FormGroup;
  loginError: string | null = null; //error message
  constructor(private fb: FormBuilder , private authService : AuthService, 
    private router : Router) { }

  ngOnInit(): void {

    this.formLogin=this.fb.group({
      username : this.fb.control(""),
      password : this.fb.control("")
    })
  }

  // handelLogin(){
  //   let username = this.formLogin.value.username;
  //   let pwd = this.formLogin.value.password;
  //   this.authService.login(username,pwd).subscribe({
  //     next : data => {
  //       this.authService.loadProfile(data);
  //       this.router.navigateByUrl("/admin/customers");
  //     },
  //     error : err => {
  //       console.log(err)
  //     }
  //   })
    
  // }

    handelLogin() {
    const username = this.formLogin.value.username;
    const pwd = this.formLogin.value.password;

    this.authService.login(username, pwd).subscribe({
      next: (data) => {
        this.authService.loadProfile(data);
        this.router.navigateByUrl('/admin/customers');
        this.loginError = null; // clear error on success
      },
      error: (err) => {
        console.error(err);
        this.loginError = err.error || 'Invalid username or password'; // ✅ show error
      }
    });
  }

}
