package com.jchc.practica2.data

import com.jchc.practica2.data.remote.OrgsApi
import com.jchc.practica2.data.remote.model.OrgDetailDto
import com.jchc.practica2.data.remote.model.OrgDto
import retrofit2.Call
import retrofit2.Retrofit

class OrgRepository(private val retrofit: Retrofit) {

    //instanciando objeto para entrar al API
    private val orgsApi: OrgsApi = retrofit.create(OrgsApi::class.java)

    fun getOrgs(url: String?): Call<List<OrgDto>> = orgsApi.getOrgs(url)

    fun getOrgDetail(id: String?): Call<OrgDetailDto> = orgsApi.getOrgDetail(id)
}