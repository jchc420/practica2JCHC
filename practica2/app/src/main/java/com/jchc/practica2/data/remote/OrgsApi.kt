package com.jchc.practica2.data.remote

import com.jchc.practica2.data.remote.model.OrgDetailDto
import com.jchc.practica2.data.remote.model.OrgDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

//Sería el equivalente al DAO en ROOM
interface OrgsApi {
    //Aquí ponemos las funciones para los endpoints
    //https://private-87ff9c-jchc.apiary-mock.com/org/org_list
    @GET
    fun getOrgs(
        @Url url: String?
    ): Call<List<OrgDto>> //y así se llamaría: getOrgs("org/org_list")
    //y junto con el BASE_URL tendría el URL completo

    //https://private-87ff9c-jchc.apiary-mock.com/org/org_detail/21357
    @GET("org/org_detail/{id}")
    fun getOrgDetail(
        @Path("id") id: String?
    ):Call<OrgDetailDto>


}
