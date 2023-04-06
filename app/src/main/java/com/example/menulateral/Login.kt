package com.example.menulateral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.util.PatternsCompat
import com.example.menulateral.ApiAcces.ApiGetUserCep
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var UserCep: List<String>
    private lateinit var lstUserCep: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lstUserCep = binding.listUsers
        val userCepApi = RetrofitClient.getInstance().create(ApiGetUserCep::class.java)

        GlobalScope.launch() {
            val call = userCepApi.getUsuarioCep()
            val response = call.execute()
            val localUserCep = response.body()
            if (localUserCep != null) {

                UserCep = localUserCep
                runOnUiThread() {
                    val adapter = ArrayAdapter(this@Login, android.R.layout.simple_list_item_1, UserCep)
                    lstUserCep.setAdapter(adapter)
                }

            } else {
                Toast.makeText(this@Login, "Error al consultar la API", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogin2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (validar()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }


    private fun validar(): Boolean {
        var next = true
        val result = arrayOf(validateEmail(), validatePassword())
        if (false in result) {
            next = false
        }
        return next
    }


    private fun validateEmail(): Boolean {
        var next = true;
        val emailTxt = binding.email.editText?.text.toString()
        if (emailTxt.isEmpty()) {
            binding.email.error = "Filed can not be empty"
            next = false;
        } else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            binding.email.error = "Please enter a valid email address"
            next = false;
        } else {
            binding.email.error = null
        }
        return next;
    }


    private fun validatePassword(): Boolean {
        var next = true;
        val passwordTxt = binding.password.editText?.text.toString()
        val passRegex = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    ".{8,}" +
                    "$"
        )
        if (passwordTxt.isEmpty()) {
            binding.password.error = "Filed can not be empty"
            next = false;
        } else if (!passRegex.matcher(passwordTxt).matches()) {
            binding.password.error = "Please enter a valid password address"
            next = false;
        } else {
            binding.password.error = null

        }
        return next;
    }

}