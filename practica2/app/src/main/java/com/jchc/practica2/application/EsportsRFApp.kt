package com.jchc.practica2.application

import android.app.Application
import com.jchc.practica2.data.OrgRepository
import com.jchc.practica2.data.remote.RetrofitHelper

class EsportsRFApp: Application() {

    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy{ OrgRepository(retrofit) }
}