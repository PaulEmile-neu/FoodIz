package fr.isen.neu.foodtrex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.neu.foodtrex.data.interfaces.ItemClickListener
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.databinding.DishCardComponentBinding
import fr.isen.neu.foodtrex.R
class DishesAdapter(
    private val dishesItemsData: List<DishModel>,
    private val itemClickListener: ItemClickListener
): RecyclerView.Adapter<DishesAdapter.DishViewHolder>() {


    var context:Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        context = parent.context
        val binding = DishCardComponentBinding.inflate(LayoutInflater.from(context))
        return DishViewHolder(binding)
    }

    override fun onBindViewHolder(holderDish: DishViewHolder, position: Int) {
        holderDish.binding.tvDishName.text = dishesItemsData[position].name_fr
        if(dishesItemsData[position].images[0].isEmpty())
        {
            R.drawable.entry
        }
        else{
            Picasso.with(context)
                .load(dishesItemsData[position].images[0])
                .error(R.drawable.dishes)
                .into(holderDish.binding.imgDish)
        }


        holderDish.itemView.setOnClickListener{
            itemClickListener.OnCardClickListener(dishesItemsData[position])
        }

    }

    override fun getItemCount(): Int {
        return dishesItemsData.size
    }

    //our ViewHolder inner class to help our adapter to bind data
    class DishViewHolder(val binding:DishCardComponentBinding): RecyclerView.ViewHolder(binding.root)
    {
        val imageView: ImageView = binding.imgDish
        val textView: TextView = binding.tvDishName

    }
}