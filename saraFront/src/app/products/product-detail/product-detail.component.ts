import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../../models/product.model';
import { ProductsService } from '../../services/products.service';
import { CartService } from '../../services/cart.service';
import { Variants } from '../../models/variants';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss']
})
export class ProductDetailComponent implements OnInit {
  @Input() public product: Product = { // Initialize product to an empty object
    id: 0,
    name: '',
    description: '',
    price: 0,
    imgURL: '',
    groupset: '',
    material: '',
    wheels: '',
    prodctVariantsList: [],
    amount: 0 // Add amount property initialization
  };
  private productId!: number;
  selectedColor: string = ''; // Initialize selectedColor
  selectedSize: any = ''; // Initialize selectedSize
  selectedPrice: any = null;
  variantImgURL: any = null; // Initialize variantImgURL
  variantQuantity: any = null;
  selectedVariant: any;

  sizes: number[] = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private productsService: ProductsService,
    private cartService: CartService
  ) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(params => {
      this.productId = params['id'];
      this.getProduct();
    });
  }

  getProduct() {
    this.productsService.getProductByIndex(this.productId).subscribe((product: Product) => {
      this.product = product;
      console.log('Product:', this.product); // Log the product object
      console.log('Available Variants:', this.product.prodctVariantsList); // Log available variants
  
      // Set default values for selectedColor and selectedSize
      // if (this.product.prodctVariantsList.length > 0) {
      //   this.selectedColor = this.product.prodctVariantsList[0].color;
      //   this.selectedSize = this.product.prodctVariantsList[0].size;
      // }
  
      this.updateVariantImageURL(); // Update the image URL on product load
    });
  }
  

  updateVariantImageURL() {
    console.log('Selected Color:', this.selectedColor); // Log the selected color
    console.log('Selected Size:', this.selectedSize); // Log the selected size
  
    if (this.product && this.product.prodctVariantsList) {
      const selectedVariant = this.product.prodctVariantsList.find(
        variant => variant.color === this.selectedColor && variant.size === this.selectedSize
      );
  
      console.log('Selected Variant:', selectedVariant); // Log the selected variant
      if (selectedVariant) {
        this.variantImgURL = selectedVariant.imgURL || 'https://www.trendcarpet.nl/images/zoom/credbilder-mall-art-silk-beige..jpg'; // Set a default image if imgURL is null or undefined
      } else {
        this.variantImgURL = this.product.imgURL || 'https://c889979.ssl.cf3.rackcdn.com/cgg/uploads/prod_img/2_9139_s.jpg?v=-62169984000'; // Set a default image if variant not found
      }
      console.log('Variant Image URL:', this.variantImgURL); // Log the variant image URL
    }
  }
  

  filterSizes(): void {
    if (this.selectedColor) {
        this.sizes = this.product.prodctVariantsList
            .filter(variant => variant.color === this.selectedColor)
            .map(variant => variant.size);
    } else {
        this.sizes = [];
    }
    this.selectedSize = ''; // Reset selected size when color changes=
  }

  getUniqueColors(): string[] {
      return [...new Set(this.product.prodctVariantsList.map(variant => variant.color))];
  }

  updateVariant(): void {
    if (this.selectedColor && this.selectedSize) {
        const selectedVariant = this.product.prodctVariantsList.find(variant =>
            variant.color == this.selectedColor && variant.size == this.selectedSize
        );
        console.log("selected variant", selectedVariant)
        this.selectedVariant = selectedVariant;
        this.selectedPrice = selectedVariant ? selectedVariant.price : null;
        this.variantQuantity = selectedVariant?.stock;
        this.variantImgURL = selectedVariant?.imgURL;
        console.log("selected variant", this.variantQuantity)
    } else {
        this.selectedPrice = null;
        this.variantImgURL = this.product.imgURL;
    }
}
  

  onColorChange() {
    this.updateVariantImageURL();
  }

  onSizeChange() {
    this.updateVariantImageURL();
  }

  public onBuyProduct(product: Product) {
    if (this.selectedVariant) {
      this.product.prodctVariantsList = [this.selectedVariant]
    }
    
    this.cartService.addProductToCart(product);
  }
}
