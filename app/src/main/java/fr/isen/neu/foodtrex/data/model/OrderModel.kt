package fr.isen.neu.foodtrex.data.model

import java.io.Serializable

/*------------------------------------------------- OrderModel -----
 |
 |  Purpose: This data class manages the order request for api
 |
 *-------------------------------------------------------------------*/
data class OrderModel(
    val data: List<OrderData>, val code: String
) : Serializable

data class OrderData(
    val id_user: String, val message: String
) : Serializable
