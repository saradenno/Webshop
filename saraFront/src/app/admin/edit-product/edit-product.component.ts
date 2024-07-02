import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { CommonModule } from '@angular/common';
import { ProductService } from '../product.service';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/product.model';
import { FormsModule } from '@angular/forms';
import { Variants } from '../../models/variants';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-edit-product',
  standalone: true,
  imports: [NavbarComponent, CommonModule, FormsModule],
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {
  
  productId!: any;
  product: Product = new Product();
  variants: Variants = new Variants();
  
  variantList:Variants[]=[];

  constructor(
    private productService: ProductService, 
    private route: ActivatedRoute
  ){}
  
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.productId = Number(params.get('id')); 
      this.getProduct();
    });
  }

  getProduct() {
    if (this.productId != null) {
      this.productService.getProductById(this.productId).subscribe(
        (response) => {
          this.product = response;
          console.log(this.product);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }

  saveVariant() {
    this.variants.productId = this.productId;
    console.log(this.variants);
    this.productService.saveVariant(this.variants).subscribe(
      (response) => {
        console.log(response);
        alert("Variant Added!");
        this.getProduct();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  addVariant(): void {
    const newVariant = new Variants();
    this.product.prodctVariantsList.push(newVariant);
  }

  updateProduct() {

    console.log(this.product); // Log the product object before updating
    this.productService.updateProduct(this.product).subscribe(
      (response) => {
        alert("Product Updated!");
        this.getProduct();
      },
      (error) => {
        console.log(error);
      }
    );
  }

  // deleteVariant(variantId: number) {
  //   this.productService.deleteVariant(variantId).subscribe(
  //     (response) => {
  //       this.product.prodctVariantsList = this.product.prodctVariantsList.filter(variant => variant.id !== variantId);
  //       console.log(`Variant with ID ${variantId} has been deleted.`);
  //     },
  //     (error: HttpErrorResponse) => {
  //       console.error(`Error deleting variant with ID ${variantId}:`, error.message);
  //       console.error('Error details:', error);
  //     }
  //   );
  // }

  deleteVariant(variantId: number) {
    this.productService.deleteVariant(variantId).subscribe(
      (response) => {
        this.product.prodctVariantsList = this.product.prodctVariantsList.filter(variant => variant.id !== variantId);
        console.log(`Variant with ID ${variantId} has been deleted.`);
        alert('Variant has been deleted'); // Voeg een alert toe

      },
      (error: HttpErrorResponse) => {
        console.error(`Error deleting variant with ID ${variantId}:`, error.message);
        console.error('Error details:', error);
      }
    );
  }
}
