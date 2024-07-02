import {Component, OnInit} from '@angular/core';
import {Product} from "../models/product.model";
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";
import {CommonModule} from "@angular/common";
import {RouterLink} from "@angular/router";
import {CoreModule} from '../core/core.module';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, RouterLink , CoreModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  public user!: User;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService
      .getUserByEmail()
      .subscribe((user: User) => {
        this.user = user;
      });
  }

}
