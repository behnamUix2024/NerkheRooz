package com.behnamuix.nerkherooz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.behnamuix.nerkherooz.databinding.AdsItemBinding
import com.behnamuix.nerkherooz.model.Ads
import com.bumptech.glide.Glide

class AdsAdpater(private val adsList: List<Ads>) :
    RecyclerView.Adapter<AdsAdpater.AdsViewHolder>() {

    inner class AdsViewHolder(val binding: AdsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = AdsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        var ads = adsList[position]
        holder.binding.tvAdsName.text = ads.name
        Glide.with(holder.itemView.context)
            .load(ads.image)
            .into(holder.binding.imgAdsCover)
    }

    override fun getItemCount(): Int {
        return adsList.size
    }
}