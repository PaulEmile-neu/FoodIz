package fr.isen.neu.foodtrex.data.model


import java.io.Serializable


/*------------------------------------------------- DishesData -----
 |
 |  Purpose: This data class manages the data from the API
 |
 *-------------------------------------------------------------------*/
data class DishesData(var data: List<CategoryModel>) : Serializable


data class CategoryModel(val name_fr: String, val items: List<DishModel>) : Serializable


data class DishModel(
    val name_fr: String,
    val images: List<String>,
    val prices: List<PriceData>,
    val ingredients: List<IngredientData>
) : Serializable


data class PriceData(val price: String) : Serializable


data class IngredientData(val name_fr: String) : Serializable

data class CartItem(var product: DishModel, var quantity: Int = 0)