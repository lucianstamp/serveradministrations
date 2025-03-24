import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login-form',
  standalone: false,
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {

  user = {username: '', password: ''};
  error: string | undefined;

  constructor(private http: HttpClient, private router: Router) {
  }

  onSubmit() {
    this.http.post('http://localhost:8080/api/user/login', this.user, {headers: {'Content-Type': 'application/json'}})
      .subscribe(response => {
        console.log('Login successful', response);
        this.router.navigate(['/dashboard']);


      }, error => {
        console.error('Login failed', error);
        this.error = 'Invalid username or password!'
      });
  }


}
