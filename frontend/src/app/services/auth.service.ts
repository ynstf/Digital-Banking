import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Route, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuthenticated : boolean = false ;
  root : String = environment.backendHost ;
  roles : any;
  username : any;
  accessToken! : any;


  constructor(private http:HttpClient,
    private router : Router
  ) { }

  public login(username : string , password : string){
    let options = {
      headers : new HttpHeaders().set("Content-Type","application/x-www-form-urlencoded")
    }
    let params = new HttpParams()
    .set("username",username).set("password",password);
    

    return this.http.post(this.root+"/auth/login",params,options)

  }

  loadProfile(data : any){
    this.isAuthenticated = true;
    this.accessToken = data['access-token'];
    let decodedJwt:any = jwtDecode(this.accessToken);
    this.username = decodedJwt.sub;
    this.roles = decodedJwt.scope;
    window.localStorage.setItem("jwt-token",this.accessToken);

  }
  

  logout(){
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.username = undefined;
    this.roles = undefined;
    window.localStorage.removeItem("jwt-token");
    this.router.navigateByUrl("/login");
  }

  loadJwtTokenFromLocalStorage(){
    let token = window.localStorage.getItem("jwt-token");
    if(token){
      this.loadProfile({"access-token":token});
      this.router.navigateByUrl("/admin/customers")
    }
  }


  // public changeUsername(data : any){
  //   this.http.put(this.root+'/profile/username', data, { responseType: 'text' })
  //       .subscribe({
  //         next: res => {alert(res);
  //                       this.logout()},
  //         error: err => alert(err.error)
  //       });
  // }



  public changeUsername(data: any) {
    if (!data.newUsername || data.newUsername.trim() === '') {
      alert("New username cannot be empty");
      return;
    }

    this.http.put(this.root + '/profile/username', data, { responseType: 'text' })
      .subscribe({
        next: res => {
          alert(res);
          this.logout();
        },
        error: err => alert(err.error || "An error occurred")
      });
}



  // public changePassword(data : any){
  //   this.http.put(this.root+'/profile/password', data, { responseType: 'text' })
  //       .subscribe({
  //         next: res => {alert(res);
  //                       this.logout()},
  //         error: err => alert(err.error)
  //       });
  // }

  public changePassword(data: any) {
    if (!data.oldPassword || !data.newPassword || data.newPassword.trim() === '') {
      alert("Old password and new password are required");
      return;
    }

    this.http.put(this.root + '/profile/password', data, { responseType: 'text' })
      .subscribe({
        next: res => {
          alert(res);
          this.logout();
        },
        error: err => {
          const errorMessage = err.error || "May be the password was incorrect";
          alert(errorMessage);
        }
      });
}


}
