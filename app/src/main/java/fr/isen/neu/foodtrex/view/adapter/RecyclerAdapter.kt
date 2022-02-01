package fr.isen.neu.foodtrex.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.neu.foodtrex.data.interfaces.ItemClickListener
import fr.isen.neu.foodtrex.data.model.DishModel
import fr.isen.neu.foodtrex.databinding.CardComponentBinding


class RecyclerAdapter(
    private val dishesItemsData: List<DishModel>,
    private val itemClickListener: ItemClickListener
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    var context:Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        context = parent.context
        val binding = CardComponentBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.binding.tvDishName.text = dishesItemsData[position].name_fr
        /*.with(context)
            .load(dishesItemsData[position].image[1])
            .error(R.drawable.ic_launcher_background)
            .into(holder.binding.imgDish)*/

        holder.itemView.setOnClickListener{
            itemClickListener.OnCardClickListener(dishesItemsData[position])
        }

    }

    override fun getItemCount(): Int {
        return dishesItemsData.size
    }

    //our ViewHolder inner class to help our adapter to bind data
    class ViewHolder( val binding:CardComponentBinding): RecyclerView.ViewHolder(binding.root)
    {
        val imageView: ImageView = binding.imgDish
        val textView: TextView = binding.tvDishName

    }
}