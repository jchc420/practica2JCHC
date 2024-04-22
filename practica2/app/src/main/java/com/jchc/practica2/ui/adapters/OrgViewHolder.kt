package com.jchc.practica2.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.jchc.practica2.data.remote.model.OrgDetailDto
import com.jchc.practica2.data.remote.model.OrgDto
import com.jchc.practica2.databinding.OrgElementBinding

class OrgViewHolder(private var binding: OrgElementBinding):
    RecyclerView.ViewHolder(binding.root){
        val ivThumbnail = binding.ivThumbnail

    fun bind(org: OrgDto){
        binding.tvTitle.text = org.title
        binding.tvCountry.text = org.country
        binding.tvTricode.text = org.tricode
        binding.tvValcoach.text = org.valCoach
    }
}
