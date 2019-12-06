import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mockup';

   userName = 'Danny';

   isLoggedIn = false;

  constructor(private router: Router) {}

  toHome() {
    this.router.navigate(['dashboard']);
  }

  logout() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('sessionkey');
    this.router.navigate(['login']);
  }

}
