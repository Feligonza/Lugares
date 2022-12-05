package com.sem08

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sem08.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //objeto Firebase Auth
    private lateinit var auth : FirebaseAuth

    //objeto pantalla xml
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar Auth
        FirebaseApp.initializeApp( this)

        //Click Registro
        binding.btRegistro.setOnClickListener{registrar()}

        //Click Login
        binding.btLogin.setOnClickListener{login()}

    }

    private fun registrar() {
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        //Registro de usuario
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    cargarPantalla(user)
                } else {
                    Toast.makeText(baseContext, "Fallo ${task.exception.toString()}", Toast.LENGTH_LONG).show()
                }
            }
    }


    private fun cargarPantalla(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this,Principal::class.java)
            startActivity(intent)
        }
    }

    private fun login(){

        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        auth.signInWithEmailAndPassword(email, clave)
            .addOnCompleteListener { result ->
                if(result.isSuccessful){
                    val user = auth.currentUser
                    cargarPantalla(user)
                }else{
                    Toast.makeText(baseContext, "Usuario y contraseña invalidos ${result.exception.toString()}", Toast.LENGTH_LONG).show()
                }
            }

    }

    override fun onStart(){
        super.onStart()

        val user = auth.currentUser
        cargarPantalla(user)

    }

}