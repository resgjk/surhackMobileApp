package com.example.kkmarketplace

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kkmarketplace.databinding.ActivitySignUpBinding
import com.example.kkmarketplace.users.User
import com.example.kkmarketplace.users.UserApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpBinding

    val connect = Retrofit.Builder().baseUrl("https://juicy-succinct-battery.glitch.me/api/")
        .addConverterFactory(GsonConverterFactory.create()).build()
    val userAPI = connect.create(UserApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.singUpBtn.setOnClickListener(::clickedRegister)

    }

    fun createDialog(msg: String) {
        val builder = AlertDialog.Builder(this)
        if (msg == "Пароли не совпадают") {
            builder.setTitle(R.string.passwordsIdntTitle)
            builder.setMessage(R.string.passwordsIdntMsg)
            builder.setNegativeButton(
                R.string.ok,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    viewBinding.firstPassword.setText("")
                    viewBinding.secondPassword.setText("")
                }
            )
        } else if (msg == "Пользователь уже зарегистрирован") {
            builder.setTitle(R.string.userInDbTitle)
            builder.setMessage(R.string.userInDbMsg)
            builder.setNegativeButton(
                R.string.ok,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    viewBinding.firstname.setText("")
                    viewBinding.secondname.setText("")
                    viewBinding.email.setText("")
                    viewBinding.firstPassword.setText("")
                    viewBinding.secondPassword.setText("")
                }
            )
        } else if (msg == "Введены не все данные") {
            builder.setTitle(R.string.notDataTitle)
            builder.setMessage(R.string.notDataMsg)
            builder.setNegativeButton(
                R.string.ok,
                DialogInterface.OnClickListener { dialogInterface, i -> }
            )
        }
        builder.show()
    }

    fun postNewUserInDataBase() {
        var status = ""
        if (viewBinding.buyerBtn.isChecked == true) {
            status = "buyer"
        } else status = "salesman"
        val newUser = User(
            firstname = viewBinding.firstname.text.toString(),
            secondname = viewBinding.secondname.text.toString(),
            email = viewBinding.email.text.toString(),
            hashed_password = viewBinding.firstPassword.text.toString(),
            status = status,
            id = 1,
            message = "ok"
        )
        CoroutineScope(Dispatchers.IO).launch {
            userAPI.postUser(newUser)
        }
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun checkUserInAllUsers(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userAPI.getUserByEmailAndPassword(email)
            if (user.email == email &&
                user.hashed_password == viewBinding.firstPassword.text.toString()
            ) {
                runOnUiThread {
                    createDialog("Пользователь уже зарегистрирован")
                }
            } else {
                runOnUiThread {
                    postNewUserInDataBase()
                }
            }
        }
    }

    fun clickedRegister(view: View) {
        if (
            viewBinding.firstname.text.toString() != "" &&
            viewBinding.secondname.text.toString() != "" &&
            viewBinding.email.text.toString() != "" &&
            viewBinding.firstPassword.text.toString() != "" &&
            viewBinding.secondPassword.text.toString() != "" &&
            (viewBinding.buyerBtn.isChecked == true || viewBinding.salesmanBtn.isChecked == true)
        ) {
            if (viewBinding.firstPassword.text.toString() ==
                viewBinding.secondPassword.text.toString()
            ) {
                checkUserInAllUsers(viewBinding.email.text.toString())
            } else createDialog("Пароли не совпадают")
        } else createDialog("Введены не все данные")
    }
}