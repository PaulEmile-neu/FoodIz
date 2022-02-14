package fr.isen.neu.foodtrex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.neu.foodtrex.data.model.OrderModel
import fr.isen.neu.foodtrex.databinding.ActivityFinalOrderBinding
import org.json.JSONObject

/*------------------------------------------------- FinalOrderActivity -----
 |
 |  Purpose: Last activity before sending order request
 |
 *-------------------------------------------------------------------*/
class FinalOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinalOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFinalOrderBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.confirmOrder.setOnClickListener {
            orderFood()
            finish()
        }

        binding.cancelOrder.setOnClickListener {
            finish()
        }


    }

    //api call to send the orderRequest
    private fun orderFood() {
        val apiURL = "http://test.api.catering.bluecodegames.com/user/order"
        val userId =
            getSharedPreferences("saveUserId", MODE_PRIVATE).getString("id_user", "").toString()
        val jsonObj = JSONObject()
        jsonObj.put("id_shop", 1)
        jsonObj.put("id_user", userId)
        jsonObj.put("msg", ShoppingCart.getCart().toString())

        //managing request queue
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST, apiURL, jsonObj, { response ->
                val orderAnswer = Gson().fromJson(response.toString(), OrderModel::class.java)
                if (orderAnswer.data != null) {
                    if (orderAnswer.code == "200") {
                        startActivity(Intent(this, HomeActivity::class.java))
                        Toast.makeText(this, "order Success", Toast.LENGTH_SHORT).show()
                        ShoppingCart.addToHistory()
                        ShoppingCart.emptyCart()
                    }
                }

            }, {
                Log.d("Order Error", "Api Order error")
            }
        )
        queue.add(request)
    }
}