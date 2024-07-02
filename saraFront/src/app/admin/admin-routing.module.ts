import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { ProductsComponent } from './products/products.component';
import { authGuard } from './auth.guard';
import { EditProductComponent } from './edit-product/edit-product.component';
import { AddProductComponent } from './add-product/add-product.component';

const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path:"edit-product/:id",
    component:EditProductComponent,
    canActivate:[authGuard]
  },
  {
    path:"add-product",
    component:AddProductComponent,
    canActivate:[authGuard]
  },
  {
    path: "admin-products",
    component: ProductsComponent,
    canActivate:[authGuard]
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: 'login'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
