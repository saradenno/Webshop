import { Component ,OnInit, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {AuthService} from '../auth.service';
import { AuthRequest } from '../auth-request.model';
import { error } from 'jquery';
import { Router } from '@angular/router';
import { TokenService } from '../token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  authReq:AuthRequest = new AuthRequest();
  tokenService: TokenService = inject(TokenService);

  constructor(private authService : AuthService , 
    private route: Router
  ){}

  ngOnInit(): void {
   
  }

  login(){
    console.log(this.authReq.email , this.authReq.password);
    //this.route.navigate(['/admin/admin-products']);
    this.authService.login(this.authReq).subscribe(
      (response)=>{
        console.log(response);
        this.route.navigate(['/admin/admin-products']);
      },
      (error)=>{
        console.log(error);
      }
    );
    
  
  }

}
