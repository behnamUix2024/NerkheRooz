package com.behnamuix.nerkherooz.adapter

import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.behnamuix.nerkherooz.databinding.RecHomeItemBinding
import com.behnamuix.nerkherooz.remote.goldModel.ContentModel
import com.behnamuix.nerkherooz.remote.goldModel.GoldModel

class RecyclerMainAdapter (
    private val alldata:ArrayList<ContentModel>

        ):RecyclerView.Adapter<RecyclerMainAdapter.MainViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view=MainViewHolder(
            RecHomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
        return view
    }

    override fun getItemCount(): Int {
     return   alldata.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.setData(alldata[position])
    }

    inner class MainViewHolder(
        private val binding: RecHomeItemBinding
    ):ViewHolder(binding.root){
        fun setData(data:ContentModel){
            binding.tvName.text=data.label
            val formatter=DecimalFormat("#,### ت")
            val formattedNumber=formatter.format((data.price/10))
            binding.tvPrice.text=formattedNumber



        }

    }
}