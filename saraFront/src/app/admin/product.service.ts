import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { Observable } from 'rxjs';
import { Variants } from '../models/variants';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl: string = environment.base_url + "/admin";

  constructor(private http: HttpClient) {
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl+"/products");
  }

  
  getProductById(id:number): Observable<Product> {
    return this.http.get<Product>(this.baseUrl+"/product/"+id);
  }

  saveVariant(variant:Variants): Observable<Variants>{
    return this.http.post<Variants>(this.baseUrl+"/saveVariant",variant);

  }

  updateProduct(product:Product): Observable<Product>{
    return this.http.put<Product>(this.baseUrl+"/updateProduct",product);
  }

  saveProduct(product:Product): Observable<Product>{
    return this.http.post<Product>(this.baseUrl+"/saveProduct",product);
  }

  // Add the deleteProduct method
  deleteProduct(productId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + "/deleteProduct/" + productId);
  }

  deleteVariant(variantId: number): Observable<void> {
    return this.http.delete<void>(this.baseUrl + "/deleteVariant/" + variantId);
  }
  

}
