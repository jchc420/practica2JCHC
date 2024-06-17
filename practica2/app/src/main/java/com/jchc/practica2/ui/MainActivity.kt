package com.jchc.practica2.ui

import android.content.Intent
import android.location.LocationListener
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jchc.practica2.R
import com.jchc.practica2.data.OrgRepository
import com.jchc.practica2.data.remote.RetrofitHelper
import com.jchc.practica2.data.remote.model.OrgDto
import com.jchc.practica2.databinding.ActivityMainBinding
import com.jchc.practica2.ui.fragments.OrgListFragment
import com.jchc.practica2.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var repository: OrgRepository
    private lateinit var retrofit: Retrofit

    private lateinit var binding: ActivityMainBinding

    //para la canción de fondo
    private lateinit var mediaPlayer: MediaPlayer

    //para Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        user = firebaseAuth?.currentUser
        userId = user?.uid

        if (user != null){
            binding.btnIniciarSesion.visibility = View.INVISIBLE
            binding.btnCerrarSesion.visibility = View.VISIBLE
            binding.tvLoggedUser.visibility = View.VISIBLE
            binding.tvUser.text = user?.email
        }else{
            binding.btnCerrarSesion.visibility = View.INVISIBLE
            binding.btnIniciarSesion.visibility = View.VISIBLE
        }

        //revisamos si el email no está verificado

        /*if(user?.isEmailVerified != true){
            binding.tvCorreoNoVerificado.visibility = View.VISIBLE
            binding.btnReenviarVerificacion.visibility = View.VISIBLE

            binding.btnReenviarVerificacion.setOnClickListener {
                user?.sendEmailVerification()?.addOnSuccessListener {
                    Toast.makeText(this, "El correo de verificación ha sido enviado", Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener {
                    Toast.makeText(this, "Error: El correo de verificación no se ha podido enviar", Toast.LENGTH_SHORT).show()
                    Log.d("LOGS", "onFailure: ${it.message}")
                }
            }
        }*/

        //para iniciar sesión
        binding.btnIniciarSesion.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        //para cerrar sesión
        binding.btnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            //finish()
        }

        //lanza el fragment

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OrgListFragment())
                .commit()
        }

        retrofit = RetrofitHelper().getRetrofit()
        repository = OrgRepository(retrofit)

        lifecycleScope.launch{
            val call: Call<List<OrgDto>> = repository.getOrgs("org/org_list")
            call.enqueue(object: Callback<List<OrgDto>> {
                override fun onResponse(p0: Call<List<OrgDto>>, response: Response<List<OrgDto>>) {
                    //Respuesta del server
                    Log.d(Constants.LOGTAG, "Respuesta recibida: ${response.body()}")
                }

                override fun onFailure(p0: Call<List<OrgDto>>, error: Throwable) {
                    //manejo de error
                    Toast.makeText(
                        this@MainActivity,
                        "Error en la conexión: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        if (!this::mediaPlayer.isInitialized){
            mediaPlayer = MediaPlayer.create(this, R.raw.valsong )
        }
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
        }
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}