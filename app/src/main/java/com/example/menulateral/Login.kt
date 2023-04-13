package com.example.menulateral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.UserCep
import com.example.menulateral.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object{
        lateinit var userLogin: UserCep
        lateinit var alumno: Alumno
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        globalFun(binding.email.editText?.text.toString())
        userLogin = UserCep(40001, "prueba", "1",1);

        binding.btnLogin.setOnClickListener {

            globalFun(binding.email.editText?.text.toString())

            if (validar()) {
                if (userLogin.tipoUser == 1){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this, Main2Activity::class.java)
                    startActivity(intent)
                    finish()
                }

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
        var next = false;
        val emailTxt = binding.email.editText?.text.toString()
        if (emailTxt.isEmpty()) {
            binding.email.error = "Filed can not be empty"
        } else if(emailTxt == userLogin.correoCep) {
            next = true;
        }else{
            binding.email.error = "Wrong email"
        }
        return next;
    }


    private fun validatePassword(): Boolean {
        var next = false;
        val passwordTxt = binding.password.editText?.text.toString()

        if (passwordTxt.isEmpty()) {
            binding.password.error = "Filed can not be empty"
        } else if(passwordTxt == userLogin.contraUserCep) {
            next = true;
        }else{
            binding.password.error = "Wrong password"
        }
        return next;
    }

    private fun globalFun(email :String ){

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        GlobalScope.launch() {
            val call = userCepApi.getUsuarioCep(email)
            val response = call.execute()
            val localUserCep = response.body()
            if (localUserCep != null) {

                runOnUiThread() {
                    userLogin.idUserCep = localUserCep.idUserCep
                    userLogin.correoCep = localUserCep.correoCep
                    userLogin.contraUserCep = localUserCep.contraUserCep
                    userLogin.tipoUser = localUserCep.tipoUser
                }

            } else {
                runOnUiThread() {

                    Toast.makeText(this@Login, "Error al consultar la API", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }

}