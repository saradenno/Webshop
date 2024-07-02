import { Component, OnInit, inject } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { TokenService } from '../token.service';
import { ProductService } from '../product.service';
import { Product } from '../../models/product.model';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-products',
  standalone: true,
  imports:[NavbarComponent,CommonModule],
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent implements OnInit{
  tokenService: TokenService = inject(TokenService);
  
  productList: Product[]=[];

  constructor(private productService:ProductService){}


  ngOnInit(): void {
    this.productService.getProducts().subscribe(
      (response)=>{
        this.productList=response;
        console.log(this.productList.length);
        console.log(this.productList);
      
      },
      (error)=>{
        console.log(error);
      }
    );
  }


  deleteProduct(productId: number): void {
    this.productService.deleteProduct(productId).subscribe(
      (response: any) => { // Specify type for response
        this.productList = this.productList.filter(product => product.id !== productId);
        console.log(`Product with ID ${productId} has been deleted.`);
      },
      (error: any) => { // Specify type for error
        console.log(`Error deleting product with ID ${productId}:`, error);
      }
    );
  }

}



