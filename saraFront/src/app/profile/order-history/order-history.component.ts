import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service'; // Pas dit pad aan naar waar je service zich bevindt
import { Order } from '../../models/order.model'; // Pas dit pad aan naar waar je model zich bevindt
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-order-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.scss']
})
export class OrderHistoryComponent implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.loadOrders();
  }

  loadOrders() {
    this.orderService.getOrdersByCurrentUser().subscribe(orders => {
      this.orders = orders;
    });
  }

  calculateTotal(orderItems: any[]): number {
    let total = 0;
    for (const orderItem of orderItems) {
      total += (orderItem.variant ? orderItem.variant.price : orderItem.product.price) * orderItem.quantity;
    }
    return Number(total.toFixed(2));
  }
}
