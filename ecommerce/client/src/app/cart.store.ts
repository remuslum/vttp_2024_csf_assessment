
// TODO Task 2
// Use the following class to implement your store

import { ComponentStore } from "@ngrx/component-store";
import { Cart, LineItem } from "./models";
import { Injectable } from "@angular/core";

const INIT_STATE:Cart = {
    lineItems: []
}

@Injectable()
export class CartStore extends ComponentStore<Cart>{

    constructor(){
        super(INIT_STATE)
    }

    readonly addItemToCart = this.updater<LineItem>(
        (store: Cart, itemToAdd: LineItem) => {
            return {
                lineItems : [...store.lineItems, itemToAdd]
            } as Cart
        }
    )

    readonly viewItemsInCart = this.select<LineItem[]>(
        (store:Cart) => {
            return store.lineItems
        }
    )

    readonly countItemsInCart = this.select<number>(
        (store:Cart) => {
            return store.lineItems.length
        }
    )

    readonly resetItems = this.updater<LineItem[]>(
        (store:Cart, itemsToAdd:LineItem[]) => {
            return {
                lineItems : itemsToAdd
            } as Cart
        }
    )
}
