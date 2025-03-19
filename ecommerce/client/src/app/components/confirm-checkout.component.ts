import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartStore } from '../cart.store';
import { Cart, LineItem, Order } from '../models';
import { Observable } from 'rxjs';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css'
})
export class ConfirmCheckoutComponent implements OnInit{
  private fb = inject(FormBuilder)
  // TODO Task 3
  protected form !: FormGroup
  protected lineItems$ !: Observable<LineItem[]>
  protected totalPrice : number = 0
  protected store = inject(CartStore)
  protected productSvc = inject(ProductService)
  protected router = inject(Router)
  protected cart!:Cart

  ngOnInit(): void {
    this.form = this.createForm()
    this.lineItems$ = this.store.viewItemsInCart
    this.lineItems$.subscribe({
      next : (data) => {
        const cart = {
          lineItems : data
        }
        this.cart = cart
        for(var item of data){
          var totalPrice = item.price * item.quantity
          this.totalPrice += totalPrice
        }
      }
    })
  }

  private createForm():FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('',[Validators.required]),
      address: this.fb.control<string>('',[Validators.required, Validators.min(3)]),
      priority: this.fb.control<boolean>(false),
      comments: this.fb.control<string>('')
    })
  }

  isFormInvalid():boolean{
    return this.form.invalid
  }

  protected processForm():void {
    const order:Order = {
      name : this.form.value.name,
      address: this.form.value.address,
      priority : this.form.value.priority,
      comments : this.form.value.comments,
      cart :this.cart
    }
    console.log("submitting")
    this.productSvc.checkout(order)
    .then((response) => {
      alert(response.orderId)
    })
    .catch((error) => alert(error.message))
    this.store.resetItems([])
    this.router.navigate(["/"])
  }
  
}
