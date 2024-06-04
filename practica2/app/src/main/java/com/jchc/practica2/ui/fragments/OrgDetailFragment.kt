package com.jchc.practica2.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jchc.practica2.R
import com.jchc.practica2.application.EsportsRFApp
import com.jchc.practica2.data.OrgRepository
import com.jchc.practica2.data.remote.model.OrgDetailDto
import com.jchc.practica2.databinding.FragmentOrgDetailBinding
import com.jchc.practica2.utils.Constants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ORG_ID = "org_id"

class OrgDetailFragment: Fragment() {

    private var _binding: FragmentOrgDetailBinding? = null
    private val binding get() = _binding!!

    private var org_id: String? = null

    private lateinit var repository: OrgRepository

    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var youTubePlayerListener: YouTubePlayerListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            org_id = args.getString(ORG_ID)
            Log.d(Constants.LOGTAG, "ID RECIBIDO: $org_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        _binding = FragmentOrgDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = (requireActivity().application as EsportsRFApp).repository

        youTubePlayerView = binding.ytPlayerView


        lifecycleScope.launch {
            org_id?.let { id ->
                val call: Call<OrgDetailDto> = repository.getOrgDetail(id)
                call.enqueue(object : Callback<OrgDetailDto> {
                    override fun onResponse(
                        p0: Call<OrgDetailDto>,
                        response: Response<OrgDetailDto>
                    ) {
                        binding.apply {
                            pbLoading.visibility = View.INVISIBLE
                            tvTitle.text = response.body()?.title
                            tvLongDesc.text = response.body()?.longDesc
                            tvCountry.text = getString(R.string.country) + " " + response.body()?.country
                            tvTricode.text = getString(R.string.tricode) + " " + response.body()?.tricode
                            tvValcoach.text = getString(R.string.valCoach) + " " + response.body()?.valCoach
                            tvValRoster.text = response.body()?.valRoster?.toString()

                            Glide.with(requireActivity())
                                .load(response.body()?.image)
                                .into(ivImage)

                            youTubePlayerView = binding.ytPlayerView
                            //lifecycle.addObserver(youTubePlayerView)

                            youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    var videoUrl: String = response.body()?.ytvideo.toString()
                                    youTubePlayer.cueVideo(videoUrl, 0f)
                                }
                            })

                        }
                    }

                    override fun onFailure(p0: Call<OrgDetailDto>, p1: Throwable) {
                        //Aquí se maneja el error sin conexión (recomendado:
                        //un fragment con un mensaje de error y un botón para reintentar la conexión
                        binding.pbLoading.visibility = View.INVISIBLE
                    }

                })
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(orgId: String) =
            OrgDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ORG_ID, orgId)
                }
            }
    }
}
