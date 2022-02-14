package fr.isen.neu.foodtrex.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.neu.foodtrex.R
import fr.isen.neu.foodtrex.data.model.LoginModel
import fr.isen.neu.foodtrex.databinding.ActivityLoginBinding
import io.paperdb.Paper
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.schedule


//used to define the active form
const val LOGIN_FORM = 0
const val SIGNUP_FORM = 1


/*------------------------------------------------- LoginActivity -----
 |
 |  Purpose: This activity manages the user login and signUp
 |
 *-------------------------------------------------------------------*/
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        Paper.init(this)

        var connectionFlag = LOGIN_FORM;
        var email: String = ""
        var password: String = ""
        var firstname: String = ""
        var lastname: String = ""
        var address: String = ""

        //update the active form to login or signUp a user

        binding.signUp.setOnClickListener {
            binding.signUp.background = getDrawable(R.drawable.switch_trck_input)
            binding.logIn.background = getDrawable(R.color.white)
            binding.logIn.setTextColor(resources.getColor(R.color.pinkColor))
            binding.signUp.setTextColor(resources.getColor(R.color.white))
            binding.signInButton.text = "Sign Up"
            binding.signUpLayout.visibility = View.VISIBLE
            binding.logInLayout.visibility = View.GONE

            connectionFlag = SIGNUP_FORM


        }

        binding.logIn.setOnClickListener {
            binding.logIn.background = getDrawable(R.drawable.switch_trck_input)
            binding.signUp.background = getDrawable(R.color.white)
            binding.signUp.setTextColor(resources.getColor(R.color.pinkColor))
            binding.logIn.setTextColor(resources.getColor(R.color.white))
            binding.signInButton.text = "Log In"
            binding.signUpLayout.visibility = View.GONE
            binding.logInLayout.visibility = View.VISIBLE

            connectionFlag = LOGIN_FORM

        }

        //when the used entered all it's info we save them and send them to the API
        binding.signInButton.setOnClickListener {
            if (connectionFlag == LOGIN_FORM) {

                email = binding.email.text.toString()
                password = binding.password.text.toString()
                if(checkEmail(email) && checkPassword(password))
                logInUser(email, password)
                finish()
            } else {

                firstname = binding.firstname.text.toString()
                lastname = binding.lastname.text.toString()
                address = binding.address.text.toString()
                password = binding.passwordSignup.text.toString()
                email = binding.emailsignUp.text.toString()
                if (checkEmail(email) && checkInput(firstname) && checkInput(lastname) && checkPassword(password) && checkInput(address)) {
                    signUpUser(firstname, lastname, email, password, address)
                    finish()
                }

            }
        }


    }


    //api call to login a user already created
    private fun logInUser(email: String, password: String) {
        val apiURL = "http://test.api.catering.bluecodegames.com/user/login"
        val jsonObj = JSONObject()
        jsonObj.put("id_shop", 1)
        jsonObj.put("email", email)
        jsonObj.put("password", password)

        //managing request queue
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST, apiURL, jsonObj, { response ->
                val logInAnswer = Gson().fromJson(response.toString(), LoginModel::class.java)

                //if the data are not null we can continue the processing
                if (logInAnswer.data != null) {
                    //update user preferences
                    saveUser(
                        logInAnswer.data.id,
                        logInAnswer.data.firstname,
                        logInAnswer.data.lastname,
                        logInAnswer.data.email
                    )

                    //according to the code answer we can load the next activity
                    if (logInAnswer.code == "200") {
                        Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT)
                            .show()
                        Timer("SettingUp", false).schedule(800) {
                            startActivity(Intent(baseContext, FinalOrderActivity::class.java))
                            //finish the current activity to free the stack
                            finish()
                        }

                    }
                } else {
                    Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show()
                }

            },
            {
                Log.d("", "API error")
            }
        )

        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)
        queue.add(request)


    }

    //api call to signup a user
    private fun signUpUser(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        address: String
    ) {
        val apiURL = "http://test.api.catering.bluecodegames.com/user/register"
        val jsonObj = JSONObject()
        jsonObj.put("id_shop", 1)
        jsonObj.put("email", email)
        jsonObj.put("password", password)
        jsonObj.put("firstname", firstname)
        jsonObj.put("lastname", lastname)
        jsonObj.put("address", address)

        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(
            Request.Method.POST, apiURL, jsonObj, { response ->
                //if the user is successsfully created we login him directly
                logInUser(email, password)
            }, {
                Log.d("Error", "Api Error")
            }
        )

        request.retryPolicy = DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)

        queue.add(request)
    }


    //method used to check mail validity
    private fun checkEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkInput(inputName: String): Boolean {
        return inputName.isNotEmpty()
    }

    //method used to check password validity (definitly not enough secure)
    private fun checkPassword(password: String): Boolean {
        return password.length >= 6
    }


    //method used to save the user preferences so we can remember him next time he uses application
    fun saveUser(id: String, firstname: String, lastname: String, email: String) {
        val userPreference = getSharedPreferences("saveUserId", MODE_PRIVATE)
        val preferenceEditor = userPreference.edit()
        preferenceEditor.putString("id_user", id)
        preferenceEditor.putString("firstname", firstname)
        preferenceEditor.putString("lastname", lastname)
        preferenceEditor.putString("email", email)
        preferenceEditor.apply()
        preferenceEditor.commit()

    }


}

