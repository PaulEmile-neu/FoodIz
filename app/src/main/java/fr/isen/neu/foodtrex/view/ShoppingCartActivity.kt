package fr.isen.neu.foodtrex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.neu.foodtrex.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}