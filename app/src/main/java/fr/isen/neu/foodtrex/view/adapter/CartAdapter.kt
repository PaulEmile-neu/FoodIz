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


class CartAdapter(
    val cartData: List<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.CartViewHolder {
        val binding = CartComponentBinding.inflate(LayoutInflater.from(parent.context))
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartData.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindItem(cartData[position])
    }

    class CartViewHolder(val binding: CartComponentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("CheckResult")
        fun bindItem(cartItem: CartItem) {

            var item = CartItem(cartItem.product)

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

                binding.btnRemove.setOnClickListener {
                    var counter: Int = cartItem.quantity

                    if (counter > 1) {
                        counter -= 1
                        Toast.makeText(itemView.context,"item removed",Toast.LENGTH_SHORT).show()
                    } else {
                        itemView.visibility = View.GONE

                    }

                    cartItem.quantity = counter
                    binding.tvCartQuantity.text = counter.toString()

                    ShoppingCart.removeItem(item, itemView.context)

                    if(ShoppingCart.getShoppingCartSize() == 0)
                    {
                        val activity : ShoppingCartActivity = itemView.context as ShoppingCartActivity
                        activity.finishMe()
                    }

                }
        }
    }
}