package fr.isen.neu.foodtrex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.neu.foodtrex.databinding.ActivityProfileUserBinding
import fr.isen.neu.foodtrex.view.adapter.CartAdapter


/*------------------------------------------------- ProfileUserActivity -----
 |
 |  Purpose: This activity is used to display the last order of the user
 |
 *-------------------------------------------------------------------*/
class ProfileUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        updateProfile()
    }

    //display last user order in the profile
    private fun updateProfile()
    {
        val recyclerView = binding.rvOrderHistory
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(ShoppingCart.getCopyCart())
        recyclerView.adapter = adapter
    }
}