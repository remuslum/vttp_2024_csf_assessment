import { Component, OnInit, inject } from '@angular/core';
import {Observable} from 'rxjs';
import {LineItem, Product} from '../models';
import {ProductService} from '../product.service';
import {ActivatedRoute} from '@angular/router';
import { CartStore } from '../cart.store';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit {

  // NOTE: you are free to modify this component

  private prodSvc = inject(ProductService)
  private activatedRoute = inject(ActivatedRoute)
  private cartStore = inject(CartStore)

  category: string = "not set"

  products$!: Observable<Product[]>

  itemsInCart$ !: Observable<LineItem[]>

  countOfItems$ !: Observable<number>

  ngOnInit(): void {
    this.category = this.activatedRoute.snapshot.params['category']
    this.products$ = this.prodSvc.getProductsByCategory(this.category)
    this.itemsInCart$ = this.cartStore.viewItemsInCart
    this.countOfItems$ = this.cartStore.countItemsInCart
  }
}
