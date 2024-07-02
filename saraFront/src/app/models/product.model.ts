import { Variants } from "./variants";

export class Product {
  public id!: number;
  public name!: string;
  public description!: string;
  public price!: number;
  public imgURL!: string;
  public amount!: number;
  public groupset!: string;
  public material!: string;
  public wheels!: string;
  public prodctVariantsList: Variants[] = []; // Array to store product variants

  // public category: Category;
}
