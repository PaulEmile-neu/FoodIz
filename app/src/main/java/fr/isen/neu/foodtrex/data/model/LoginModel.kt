package fr.isen.neu.foodtrex.data.model

import java.io.Serializable

/*------------------------------------------------- RecipeFragment -----
 |
 |  Purpose: This data class manages the login data from api
 |
 *-------------------------------------------------------------------*/
data class LoginModel(
    val data: LoginData, val code: String
) : Serializable

data class LoginData(var id: String, var firstname: String, var lastname: String, var email:String): Serializable