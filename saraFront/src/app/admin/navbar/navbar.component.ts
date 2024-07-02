import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{

  constructor(private authService:AuthService , private router:Router){}
  
  ngOnInit(): void {
    
  }

  logout(){
    this.authService.logOut();
    location.href="/admin";
  }
}
