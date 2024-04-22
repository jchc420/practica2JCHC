package com.jchc.practica2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jchc.practica2.data.remote.model.OrgDto
import com.jchc.practica2.databinding.OrgElementBinding

class OrgAdapter(
    private val orgs: List<OrgDto>,
    private val onOrgClicked: (OrgDto) -> Unit
): RecyclerView.Adapter<OrgViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgViewHolder {
        val binding = OrgElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrgViewHolder(binding)
    }

    override fun getItemCount(): Int = orgs.size

    override fun onBindViewHolder(holder: OrgViewHolder, position: Int) {
        val org = orgs[position]
        holder.bind(org)

        //Cargamos con GLIDE la imagen al imageview
        Glide.with(holder.itemView.context)
            .load(org.thumbnail)
            .into(holder.ivThumbnail)

        holder.itemView.setOnClickListener(){
            onOrgClicked(org)
        }
    }
}
