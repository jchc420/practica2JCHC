package com.jchc.practica2.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}