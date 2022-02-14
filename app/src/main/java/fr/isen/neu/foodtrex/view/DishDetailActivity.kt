package fr.isen.neu.foodtrex.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import fr.isen.neu.foodtrex.R
import fr.isen.neu.foodtrex.data.model.CartItem
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.databinding.ActivityDishDetailBinding
import fr.isen.neu.foodtrex.view.adapter.ViewPagerAdapter
import java.util.*
import kotlin.concurrent.schedule

class DishDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDishDetailBinding
    private  lateinit var bottomNavigation: BottomNavigationView
    private lateinit var badge:BadgeDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation = binding.bottomNavBar
        badge = bottomNavigation.getOrCreateBadge(R.id.cart)
        badge.number = 0 + ShoppingCart.getShoppingCartSize()

        bottomNavigation.setOnItemReselectedListener {
            item ->
            when(item.itemId)
            {
                R.id.cart ->
                {
                    startActivity(Intent(this,ShoppingCartActivity::class.java))
                    finish()
                }

                R.id.back ->{
                    finish()
                }
            }
        }


        badge.isVisible = true

        binding.alwaysOnTop.bringToFront()

        var dishDetailData: DishModel? = intent.getSerializableExtra("dishData") as DishModel?



        binding.tvDishName.text = dishDetailData!!.name_fr

        val imagesCarousel: List<String> = dishDetailData.images
        val adapter = ViewPagerAdapter(this, imagesCarousel)
        binding.pagerCaroussel.adapter = adapter


        binding.tvDishPrice.text = dishDetailData!!.prices[0].price + " €"
        for (index in dishDetailData.ingredients)
            binding.tvDishIngredient.append(index.name_fr + "\n")

        updatePriceCounter(dishDetailData.prices[0].price, dishDetailData)


    }


    @SuppressLint("SetTextI18n")
    fun updatePriceCounter(value: String, dishDetailData: DishModel) {
        //Init our view
        var counter: Int = 1
        var priceCounter: Float = value.toFloat()
        binding.tvCounter.text = priceCounter.toString() + "€"
        binding.dishNumberQuantity.text = counter.toString()

        binding.btnAdd.setOnClickListener {
            priceCounter += value.toFloat()
            counter += 1
            if (counter > 10) {
                Toast.makeText(this, "Cannot add more", Toast.LENGTH_LONG).show()
                counter = 10
                priceCounter = counter * value.toFloat()
            }
            binding.tvCounter.text = priceCounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()
        }

        binding.btnRemove.setOnClickListener {
            if (counter > 1) {
                counter -= 1
                priceCounter -= value.toFloat()
            } else {
                priceCounter = value.toFloat()
                counter = 1
            }

            binding.tvCounter.text = priceCounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()

        }

        binding.addToCart.setOnClickListener {


            badge.number = counter + ShoppingCart.getShoppingCartSize()

            var productItem = CartItem((dishDetailData))

            ShoppingCart.addItem(productItem, counter)

            Snackbar.make(
                binding.layoutParent, "$counter  ${productItem.product.name_fr} added to your cart",
                Snackbar.LENGTH_LONG
            ).show()

            //reset variable
            counter = 1
            priceCounter = value.toFloat()
            binding.tvCounter.text = priceCounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()


            Timer("SettingUp", false).schedule(1200) {

            }


        }


    }


}