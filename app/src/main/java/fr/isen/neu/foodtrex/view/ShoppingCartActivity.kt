package fr.isen.neu.foodtrex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.neu.foodtrex.data.model.CartItem
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.databinding.ActivityShoppingCartBinding
import fr.isen.neu.foodtrex.view.adapter.CartAdapter
import fr.isen.neu.foodtrex.view.adapter.DishesAdapter

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var adapter: CartAdapter

    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateShoppingCart()

    }

    private fun updateShoppingCart() {

        if(ShoppingCart.getShoppingCartSize() == 0)
        {

            binding.emptyLayout.visibility = View.VISIBLE
            binding.emptyCartButton.setOnClickListener {
                finish()
            }
            binding.rvShoppingCart.visibility = View.GONE
        }
        else{

            binding.rvShoppingCart.visibility = View.VISIBLE
            binding.emptyLayout.visibility = View.GONE
        }
        val recyclerView = binding.rvShoppingCart
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(ShoppingCart.getCart())
        recyclerView.adapter = adapter

    }

    fun finishMe() { finish() }

}