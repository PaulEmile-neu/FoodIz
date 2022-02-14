package fr.isen.neu.foodtrex.view

import android.content.Context
import fr.isen.neu.foodtrex.data.model.CartItem
import io.paperdb.Paper

/*------------------------------------------------- ShoppingCart -----
 |
 |  Purpose: This class contains all the logic to manage cart items
 |
 *-------------------------------------------------------------------*/
class ShoppingCart {
    companion object {

        //add an item to the cart based on it's quantity
        fun addItem(cartItem: CartItem, quantity: Int) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.product.name_fr == cartItem.product.name_fr }
            if (targetItem == null) {
                cartItem.quantity += quantity
                cart.add(cartItem)
            } else {
                targetItem.quantity += quantity
            }
            saveCart(cart)
        }

        //remove an item and update quantity
        fun removeItem(cartItem: CartItem, context: Context) {
            val cart = getCart()

            val targetItem = cart.singleOrNull { it.product.name_fr == cartItem.product.name_fr }
            if (targetItem != null) {
                if (targetItem.quantity > 1) {
                    targetItem.quantity--
                } else {
                    cart.remove(targetItem)
                }
            }

            saveCart(cart)
        }

        //emtpy all the cart
        fun emptyCart() {
            val cart = getCart()
            cart.removeAll(cart)
            saveCart(cart)


        }

        //save cart date using paperDb
        fun saveCart(cart: MutableList<CartItem>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartItem> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun addToHistory() {

            val cart = getCart()
            copyCart(cart)


        }

        fun copyCart(cart: MutableList<CartItem>) {
            Paper.book().write("history", cart)
        }

        fun getCopyCart(): MutableList<CartItem> {
            return Paper.book().read("history", mutableListOf())
        }

        //get the cart size for testing purpose
        fun getShoppingCartSize(): Int {
            var cartSize = 0
            getCart().forEach {
                cartSize += it.quantity;
            }

            return cartSize
        }
    }
}