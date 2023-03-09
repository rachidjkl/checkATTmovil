package com.example.menulateral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.view.LayoutInflater
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.core.util.PatternsCompat
import com.example.menulateral.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class Login : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun validar(){
        
    }


    private fun validateEmail() : Boolean{
        var next = true;
        val emailTxt = binding.email.editText?.text.toString()
        if (emailTxt.isEmpty()){
            binding.email.error = "Filed can not be empty"
            next = false;
        }else if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailTxt).matches()){
            binding.email.error = "Please enter a valid email address"
            next = false;
        }else {
            binding.email.error = null
        }
        return next;
    }


    private fun validatePassword() : Boolean{
        var next = true;
        val passwordTxt = binding.password.editText?.text.toString()
        val passRegex = Pattern.compile(
            "^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-z])"+
                    "(?=.*[A-Z])"+
                    ".{8,}"+
                    "$"
        )
        if (passwordTxt.isEmpty()){
            binding.password.error = "Filed can not be empty"
            next = false;
        }else if (passRegex.matcher(passwordTxt).matches()){
            binding.password.error = "Please enter a valid password address"
            next = false;
        }else {
            binding.password.error = null

        }
        return next;
    }

}