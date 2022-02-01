package fr.isen.neu.foodtrex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.isen.neu.foodtrex.R
import fr.isen.neu.foodtrex.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)



        binding.signUp.setOnClickListener {
            binding.signUp.background = getDrawable(R.drawable.switch_trck)
            binding.signUpLayout.visibility = View.VISIBLE
            binding.logInLayout.visibility = View.GONE
        }

        binding.logIn.setOnClickListener {

        }
    }
}