package com.jchc.practica2.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jchc.practica2.R
import com.jchc.practica2.application.EsportsRFApp
import com.jchc.practica2.data.OrgRepository
import com.jchc.practica2.data.remote.model.OrgDto
import com.jchc.practica2.databinding.FragmentOrgListBinding
import com.jchc.practica2.ui.adapters.OrgAdapter
import com.jchc.practica2.utils.Constants
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrgListFragment: Fragment() {
    private var _binding: FragmentOrgListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: OrgRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate the layout for this fragment
        _binding = FragmentOrgListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    //El usuario ya ve el fragment en pantalla
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = (requireActivity().application as EsportsRFApp).repository

        lifecycleScope.launch {
            //https://private-87ff9c-jchc.apiary-mock.com/org/org_list
            val call: Call<List<OrgDto>> = repository.getOrgs("org/org_list")
            call.enqueue(object: Callback<List<OrgDto>>{
                override fun onResponse(p0: Call<List<OrgDto>>, response: Response<List<OrgDto>>) {
                    //respuesta del server
                    binding.pbLoading.visibility = View.GONE
                    Log.d(Constants.LOGTAG, "Respuesta recibida: ${response.body()}")

                    response.body()?.let{orgs ->
                        binding.rvOrgs.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = OrgAdapter(orgs){org ->
                                //aquí va la operación para el click de cada elemento
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, OrgDetailFragment.newInstance(org.id.toString()))
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }

                override fun onFailure(p0: Call<List<OrgDto>>, error: Throwable) {
                    //Manejo del error
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error en la conexión: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }
}