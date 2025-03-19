import { Component, OnInit, inject } from '@angular/core';
import {map, Observable} from 'rxjs';
import {Router} from '@angular/router';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  private store = inject(CartStore)

  itemCount$!: Observable<number>
  isCartEmpty:boolean = true

  ngOnInit(): void {
    this.itemCount$ = this.store.countItemsInCart
    this.itemCount$.subscribe({
      next: (data) => {
        this.isCartEmpty = data === 0
      }
    })
  }

  checkout(): void {
    this.router.navigate([ '/checkout' ])
  }

  checkCartCount():boolean {
    return this.isCartEmpty
  }
}
