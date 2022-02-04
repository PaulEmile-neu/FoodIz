package fr.isen.neu.foodtrex.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import fr.isen.neu.foodtrex.R
import fr.isen.neu.foodtrex.data.model.CartItem
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.databinding.ActivityDishDetailBinding
import io.paperdb.Paper
import java.util.*
import kotlin.concurrent.schedule

class DishDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDishDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alwaysOnTop.bringToFront()

        var dishDetailData: DishModel? = intent.getSerializableExtra("dishData") as DishModel?



        binding.tvDishName.text = dishDetailData!!.name_fr
        binding.tvDishPrice.text = dishDetailData!!.prices[0].price + " €"
        for (index in dishDetailData.ingredients)
            binding.tvDishIngredient.append(index.name_fr + "\n")

        updatePriceCounter(dishDetailData.prices[0].price, dishDetailData)


    }


    @SuppressLint("SetTextI18n")
    fun updatePriceCounter(value: String, dishDetailData: DishModel) {
        //Init our view
        var counter: Int = 1
        var Pricecounter: Float = value.toFloat()
        binding.tvCounter.text = Pricecounter.toString() + "€"
        binding.dishNumberQuantity.text = counter.toString()

        binding.btnAdd.setOnClickListener {
            Pricecounter += value.toFloat()
            counter += 1
            if(counter > 10)
            {
                Toast.makeText(this,"Cannot add more",Toast.LENGTH_LONG).show()
                counter = 10
                Pricecounter = counter*value.toFloat()
            }
            binding.tvCounter.text = Pricecounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()
        }

        binding.btnRemove.setOnClickListener {
            if (counter > 1) {
                counter -= 1
                Pricecounter -= value.toFloat()
            } else {
                Pricecounter = value.toFloat()
                counter = 1
            }

            binding.tvCounter.text = Pricecounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()

        }

        binding.addToCart.setOnClickListener {


            var bottomNavigation:BottomNavigationView = binding.bottomNavBar
            var badge = bottomNavigation.getOrCreateBadge(R.id.cart)
            badge.isVisible = true
            badge.number = counter + ShoppingCart.getShoppingCartSize()

            var productItem = CartItem((dishDetailData))

            ShoppingCart.addItem(productItem, counter)

            Snackbar.make(
                binding.layoutParent, "$counter  ${productItem.product.name_fr} added to your cart",
                Snackbar.LENGTH_LONG
            ).show()

            //reset variable
             counter= 1
            Pricecounter = value.toFloat()
            binding.tvCounter.text = Pricecounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()


            Timer("SettingUp", false).schedule(1200) {
                finish()
            }




        }


    }


}