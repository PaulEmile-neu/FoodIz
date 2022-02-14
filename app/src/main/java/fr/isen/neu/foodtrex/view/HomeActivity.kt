package fr.isen.neu.foodtrex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import fr.isen.neu.foodtrex.databinding.ActivityHomeBinding
import io.paperdb.Paper


/*------------------------------------------------- HomeActivity -----
 |
 |  Purpose: This activity is the entrypoint of the app
 |
 *-------------------------------------------------------------------*/
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
            updateCategory(str)

        }

        //updating to the next category
        binding.dishId.setOnClickListener {
            val str: String = "Plats"
            updateCategory(str)

        }
        binding.dessertId.setOnClickListener {
            val str :String = "Desserts"
            updateCategory(str)


        }

        //if the user wants to access his shopping Cart
        binding.shoppingCart.setOnClickListener {
            startActivity(Intent(this,ShoppingCartActivity::class.java))
        }

        //if the user wants to cjeck it's last order
        binding.profileId.setOnClickListener {
            startActivity(Intent(this,ProfileUserActivity::class.java))
        }

        //used to display the user name if the user preferences are saved (meaning if the user is already logged in)
        val userName = getSharedPreferences("saveUserId", MODE_PRIVATE).getString("firstname","").toString()
        if(userName != "")
        {
            binding.userName.text = userName
            binding.logout.visibility = VISIBLE
        }
        else{
            binding.userName.text = ""
            binding.logout.visibility = GONE
        }

        binding.logout.setOnClickListener {
            val userPreference = getSharedPreferences("saveUserId", MODE_PRIVATE)
            userPreference.edit().clear().commit();
        }

    }

    //used to change the category dynamically
    private fun updateCategory(str : String) {
        val intent =  Intent(this,CategoryActivity::class.java)
        intent.putExtra("selectedCategory", str)
        startActivity(intent)


    }







}