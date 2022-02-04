package fr.isen.neu.foodtrex.view

import android.content.Context
import fr.isen.neu.foodtrex.data.model.CartItem
import io.paperdb.Paper

class ShoppingCart {
    companion object {
        fun addItem(cartItem: CartItem,quantity:Int) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.product.name_fr == cartItem.product.name_fr }
            if (targetItem == null) {
                cartItem.quantity+=quantity
                cart.add(cartItem)
            } else {
                targetItem.quantity+=quantity
            }
            ShoppingCart.saveCart(cart)
        }

        fun removeItem(cartItem: CartItem, context: Context) {
            val cart = ShoppingCart.getCart()

            val targetItem = cart.singleOrNull { it.product.name_fr == cartItem.product.name_fr }
            if (targetItem != null) {
                if (targetItem.quantity > 1) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }

            ShoppingCart.saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            ShoppingCart.getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }
}