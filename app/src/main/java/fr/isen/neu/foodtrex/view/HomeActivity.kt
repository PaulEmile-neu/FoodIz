package fr.isen.neu.foodtrex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.neu.foodtrex.databinding.ActivityHomeBinding
import io.paperdb.Paper

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this)

        // set on-click listener
        binding.entryId.setOnClickListener {
            val str : String = "Entrées"
            UpdateCategory(str)

        }
        binding.dishId.setOnClickListener {
            val str: String = "Plats"
            UpdateCategory(str)

        }
        binding.dessertId.setOnClickListener {
            val str :String = "Desserts"
            // your code to perform when the user clicks on the TextView
            UpdateCategory(str)


        }

        binding.shoppingCart.setOnClickListener {
            startActivity(Intent(this,ShoppingCartActivity::class.java))
        }

    }

    fun UpdateCategory(str : String) {
        val intent =  Intent(this,CategoryActivity::class.java)
        intent.putExtra("selectedCategory", str)
        startActivity(intent)


    }







}