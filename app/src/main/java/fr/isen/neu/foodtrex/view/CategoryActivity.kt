package fr.isen.neu.foodtrex.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import fr.isen.neu.foodtrex.data.interfaces.ItemClickListener
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.data.model.DishesData
import fr.isen.neu.foodtrex.databinding.ActivityCategoryBinding
import fr.isen.neu.foodtrex.view.adapter.RecyclerAdapter
import org.json.JSONObject

class CategoryActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityCategoryBinding
    //can be null
    var category:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if(intent.hasExtra("selectedCategory")){
            category = intent.getStringExtra("selectedCategory")
        }

        binding.category.text = category

        apiCall(category)

    }


    //api call using volley
    private fun apiCall(category:String?){
        val apiURL = "http://test.api.catering.bluecodegames.com/menu"
        val jsonObj = JSONObject()
        jsonObj.put("id_shop",1)

        //managing request queue
        val queue = Volley.newRequestQueue(this)

        //init our json object
        val jsonObject = JsonObjectRequest(
            Request.Method.POST,apiURL,jsonObj,
            //in case of success
            { response ->
                val dishesResponse = Gson().fromJson(response.toString(), DishesData::class.java)
                updateView(dishesResponse.data.firstOrNull{it.name_fr == category}?.items ?: listOf())
            },
            //in case of failure
            {
                    error -> error.printStackTrace()
                Log.d("HomeActivity","error while accessing api")
            }
        )
        queue.add(jsonObject)
    }


    private fun updateView(dishesData: List<DishModel>)
    {
        val recyclerView = binding.rvCategory
        recyclerView.layoutManager = GridLayoutManager(this,2)
        val adapter = RecyclerAdapter(dishesData,this)
        recyclerView.adapter = adapter
    }

         override fun OnCardClickListener(dishData: DishModel) {
            val intent  =  Intent(this,DishDetail::class.java)
            intent.putExtra("dishData", dishData)
            startActivity(intent)
        }





}