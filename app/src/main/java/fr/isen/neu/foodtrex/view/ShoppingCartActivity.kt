package fr.isen.neu.foodtrex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.neu.foodtrex.databinding.ActivityShoppingCartBinding
import fr.isen.neu.foodtrex.view.adapter.CartAdapter
import io.paperdb.Paper


/*------------------------------------------------- ShoppingCartActivity -----
 |
 |  Purpose: This activity contain all the orders
 |
 *-------------------------------------------------------------------*/
class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paper.init(this)

        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateShoppingCart()

    }

    private fun updateShoppingCart() {

        //if the cart is empty we invite the user to order
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

        binding.orderBtn.setOnClickListener {

            val userId = getSharedPreferences("saveUserId", MODE_PRIVATE).getString("email","").toString()
            Log.d("id_user", userId)
            if(userId != "")
            {
                startActivity(Intent(this,FinalOrderActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }

        }

        //allows the user to clear all the orders in the cart
        binding.clearCart.setOnClickListener {
            ShoppingCart.emptyCart()
            finish()
        }

    }

    //the method is used to close the cart if it's empty
    fun closeEmptyCart() { finish() }

}