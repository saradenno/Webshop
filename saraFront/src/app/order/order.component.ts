import { Component, OnInit } from '@angular/core';
import { CartService } from "../services/cart.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { Router } from "@angular/router";
import { Product } from '../models/product.model';
import { Order } from '../models/order.model';
import { OrderItem } from '../models/orderitem.model';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {
  public bestelForm!: FormGroup;
  public products_in_cart!: Product[];
  public order!: Order;
  public orderItems: OrderItem[] = [];

  constructor(private cartService: CartService, private router: Router, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.products_in_cart = this.cartService.allProductsInCart();
    this.bestelForm = this.fb.group({
      Voornaam: ['', [Validators.required]],
      Tussenvoegsel: [''],
      Achternaam: ['', [Validators.required]],
      Postcode: ['', [Validators.required]],
      Huisnummer: ['', [Validators.required, Validators.maxLength(5)]],
      Opmerkingen: ['']
    });
  }

  public clearCart() {
    this.cartService.clearCart();
  }

  public onSubmit() {
      const formData = this.bestelForm.value;

      this.products_in_cart.forEach(product => {
        const orderItem = new OrderItem();

        orderItem.product = product;
        orderItem.quantity = product.amount;
        orderItem.variant = product.prodctVariantsList.length > 0 ? product.prodctVariantsList[0] : null;

        this.orderItems.push(orderItem)
      })
      this.order = {
        name: formData.Voornaam,
        infix: formData.Tussenvoegsel,
        last_name: formData.Achternaam,
        zipcode: formData.Postcode,
        houseNumber: formData.Huisnummer,
        notes: formData.Opmerkingen,
        orderDate: new Date(),
        orderItems: this.orderItems
      };
      console.log('order sending ',this.order);
      this.cartService.addOrder(this.order).subscribe(
        (result) => {
          console.log('Order added successfully:', result);
          this.clearCart();
          this.router.navigateByUrl('/paymentsuccessful');
        },
        (error) => {
          console.error('Failed to add order:', error);
        }
      );

  }
}
