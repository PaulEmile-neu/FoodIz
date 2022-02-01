package fr.isen.neu.foodtrex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.databinding.ActivityDishDetailBinding

class DishDetail : AppCompatActivity() {

    private lateinit var binding: ActivityDishDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDishDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dishDetailData:DishModel? = intent.getSerializableExtra("dishData") as DishModel?



        binding.tvDishName.text = dishDetailData!!.name_fr
        binding.tvDishPrice.text = dishDetailData!!.prices[0].price + " €"
        for (index in dishDetailData.ingredients)
            binding.tvDishIngredient.append(index.name_fr + "\n" )

        updatePriceCounter(dishDetailData.prices[0].price)
    }


    fun updatePriceCounter(value: String){
        var Pricecounter: Int = 0
        var counter: Int = 0

        binding.btnAdd.setOnClickListener {
            Pricecounter += value.toInt()
            counter +=1
            binding.tvCounter.text = Pricecounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()
        }

        binding.btnRemove.setOnClickListener {
            if (counter > 0)
            {
                counter -=1
                Pricecounter -= value.toInt()
            }
            else {
                Pricecounter = 0
                counter = 0
            }

            binding.tvCounter.text = Pricecounter.toString() + "€"
            binding.dishNumberQuantity.text = counter.toString()
        }
    }


}