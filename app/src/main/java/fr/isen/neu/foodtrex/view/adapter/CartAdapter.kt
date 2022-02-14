package fr.isen.neu.foodtrex.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.neu.foodtrex.R
import fr.isen.neu.foodtrex.data.model.CartItem
import fr.isen.neu.foodtrex.databinding.CartComponentBinding
import fr.isen.neu.foodtrex.view.ShoppingCart
import fr.isen.neu.foodtrex.view.ShoppingCartActivity

/*------------------------------------------------- CartAdapter -----
 |
 |  Purpose: This adapter is used to manage the user's cart
 |
 *-------------------------------------------------------------------*/
class CartAdapter(
    val cartData: List<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartComponentBinding.inflate(LayoutInflater.from(parent.context))
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartData.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindItem(cartData[position])
    }

    /*------------------------------------------------- CartViewHolder -----
 |
 |  Purpose: This class contains the logic to update the cart according the user's choice
 |
 *-------------------------------------------------------------------*/
    class CartViewHolder(val binding: CartComponentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("CheckResult")
        fun bindItem(cartItem: CartItem) {

            var item = CartItem(cartItem.product)

            //if we have no image to display from the api we set a default one
            if (cartItem.product.images[0].isEmpty()) {
                R.drawable.entry
            } else {
                Picasso.with(itemView.context)
                    .load(cartItem.product.images[0])
                    .error(R.drawable.dishes)
                    .into(binding.imgDish)
            }
            binding.tvCartName.text = cartItem.product.name_fr

            binding.tvCartQuantity.text = cartItem.quantity.toString()


            //used to remove article from the cart
            binding.btnRemove.setOnClickListener {
                var counter: Int = cartItem.quantity

                if (counter > 1) {
                    counter -= 1
                    Toast.makeText(itemView.context, "item removed", Toast.LENGTH_SHORT).show()
                } else {
                    itemView.visibility = View.GONE

                }

                cartItem.quantity = counter
                binding.tvCartQuantity.text = counter.toString()

                ShoppingCart.removeItem(item, itemView.context)

                //if the cart is empty we close if by default
                if (ShoppingCart.getShoppingCartSize() == 0) {
                    val activity: ShoppingCartActivity = itemView.context as ShoppingCartActivity
                    activity.closeEmptyCart()
                }

            }

            //default behaviours -- may be updated soon
            binding.btnAdd.setOnClickListener {

                Toast.makeText(itemView.context, "cannot add more", Toast.LENGTH_SHORT).show()

            }

        }
    }
}