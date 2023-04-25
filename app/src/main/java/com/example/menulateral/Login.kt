package com.example.menulateral

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.menulateral.ApiAcces.ApiGets
import com.example.menulateral.ApiAcces.RetrofitClient
import com.example.menulateral.DataModel.Alumno
import com.example.menulateral.DataModel.FaltaToShow
import com.example.menulateral.DataModel.Profe
import com.example.menulateral.DataModel.UserCep
import com.example.menulateral.databinding.ActivityLoginBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    companion object{
        lateinit var userLogin: UserCep
        lateinit var alumno: Alumno
        lateinit var profe: Profe
    }



    fun callApiUserCep(email:String) = runBlocking {
        var userLogged = globalFun1(email)
        if (userLogged == null){
            userLogin = UserCep(40001, "ERROR", "1",1)
            Toast.makeText(this@Login, "Error al consultar la Usercep", Toast.LENGTH_SHORT).show()
        }else{
            userLogin = userLogged
        }

    }

    fun callApiAlumno(idUserCep: Int) = runBlocking {
        var alumnoLogged = globalFun2(idUserCep)
        if (alumnoLogged == null){
            alumno = Alumno(20021,"12345A", "ERROR","");
            Toast.makeText(this@Login, "Error al consultar el Alumno", Toast.LENGTH_SHORT).show()
        }else{
            alumno = alumnoLogged
        }

    }



    fun callApiProfe(idUserCep: Int) = runBlocking {
        var ProfeLogged = globalFun3(idUserCep)
        if (ProfeLogged == null){
            profe = Profe(20021,"12345A", "ERROR");
            Toast.makeText(this@Login, "Error al consultar el Alumno", Toast.LENGTH_SHORT).show()
        }else{
            profe = ProfeLogged.get(0)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        alumno = Alumno(20021,"12345A", "Ana","Ramon");


        binding.btnLogin.setOnClickListener {

            callApiUserCep(binding.email.editText?.text.toString())

        if (userLogin.tipoUser == 2){
            callApiAlumno(userLogin.idUserCep)

        }else{
            callApiProfe(userLogin.idUserCep)
        }


            if (validar()) {
                if (userLogin!!.tipoUser == 2){
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

    private suspend fun globalFun1(email :String ):UserCep? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getUsuarioCep(email)
            val response = call.execute()
            response.body()
        }.await()
    }


    private suspend fun globalFun2(idUserCep :Int):Alumno? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getAlumno(idUserCep)
            val response = call.execute()
            response.body()
        }.await()
    }

    private suspend fun globalFun3(id_usuario_cep :Int):List<Profe>? {

        val userCepApi = RetrofitClient.getInstance().create(ApiGets::class.java)

        return GlobalScope.async {
            val call = userCepApi.getProfesor(id_usuario_cep)
            val response = call.execute()
            response.body()
        }.await()
    }



}