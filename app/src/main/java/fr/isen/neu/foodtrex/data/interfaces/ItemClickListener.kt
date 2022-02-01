package fr.isen.neu.foodtrex.data.interfaces

import fr.isen.neu.foodtrex.data.model.DishModel

interface ItemClickListener {

    fun OnCardClickListener(dishData: DishModel)
}