package com.example.kkmarketplace

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kkmarketplace.databinding.ActivityLoginBinding
import com.example.kkmarketplace.users.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBindnig: ActivityLoginBinding

    val connect = Retrofit.Builder().baseUrl("https://juicy-succinct-battery.glitch.me/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val userAPI = connect.create(UserApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBindnig = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBindnig.root)
        viewBindnig.singIn.setOnClickListener(::signInButtonClick)
        viewBindnig.singUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }


    fun createDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.singInDialogTitle)
        builder.setMessage(R.string.singInDialogMessage)
        builder.setNegativeButton(
            R.string.ok,
            DialogInterface.OnClickListener { dialogInterface, i ->
                viewBindnig.email.setText("")
                viewBindnig.password.setText("")
            }
        )
        builder.show()
    }

    fun userSignInAsBuyer(email: String, password: String, id: Int) {
        val newIntent = Intent(this, MainActivity::class.java)
        newIntent.putExtra("userEmail", email)
        newIntent.putExtra("userPassword", password)
        newIntent.putExtra("userId", id)
        startActivity(newIntent)
    }

    fun userSignInAsSalesMan(email: String, password: String, id: Int) {
        //val newIntent = Intent(this, MainActivity::class.java)
        //newIntent.putExtra("userEmail", email)
        //newIntent.putExtra("userPassword", password)
        //newIntent.putExtra("userId", id)
        //startActivity(newIntent)
        val a = id
    }


    fun chekUserInAllUsers(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userAPI.getUserByEmailAndPassword(email)
            if (user.email == email && user.hashed_password == password) {
                if (user.status == "buyer") {
                    runOnUiThread {
                        userSignInAsBuyer(email, password, user.id)
                    }
                } else if (user.status == "salesman") {
                    runOnUiThread {
                        userSignInAsSalesMan(email, password, user.id)
                    }
                }
            } else if (user.message == "user with email $email not found") {
                runOnUiThread {
                    createDialog()
                }
            }
        }
    }

    fun signInButtonClick(view: View) {
        if (viewBindnig.email.text.toString() != "" &&
            viewBindnig.password.text.toString() != ""
        ) {
            chekUserInAllUsers(
                viewBindnig.email.text.toString(),
                viewBindnig.password.text.toString()
            )
        } else {
            createDialog()
        }
    }
}
