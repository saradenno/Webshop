import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { Product } from '../../models/product.model';
import { Variants } from '../../models/variants';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [NavbarComponent,CommonModule ,FormsModule],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.scss'
})
export class AddProductComponent implements OnInit{
 
  productId!:any;
  product:Product = new Product();
  variants : Variants = new Variants();
  variantList:Variants[]=[];

  constructor(private productService:ProductService){}

  ngOnInit(): void {
    
  }

  addVariant(): void {
    this.variantList.push(new Variants());
  }

  AddProduct(){
    this.product.prodctVariantsList=this.variantList;
    console.log(this.product);
    this.productService.saveProduct(this.product).subscribe(
      (response)=>{
        console.log(response);
        alert("Product has been added!");
        location.reload();
      },
      (error)=>{
        console.log(error);
      }
    );
  }

}
