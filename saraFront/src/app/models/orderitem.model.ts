import { Product } from "./product.model";
import { Variants } from "./variants";

export class OrderItem {
  public id?: number;
  public quantity!: Number;
  public product!: Product;
  public variant: Variants | null;

}
