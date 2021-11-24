package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.callbacks.InfoItemListener
import com.fh.info.callbacks.InfoOptionsListener
import com.fh.info.data.model.InfoItems
import com.fh.info.data.model.InfoOptions
import com.fh.info.data.model.Information
import com.fh.info.databinding.AdapterAppInfoItemsBinding
import com.fh.info.databinding.AdapterInformationBinding
import com.fh.info.databinding.AdpterAppInfoOptionsBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterInformation() : RecyclerView.Adapter<AdapterInformation.InformationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val binding =
            AdapterInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InformationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val information = differ.currentList[position]
        if (information != null) {
            holder.bind(information)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

   inner class InformationViewHolder(private val binding: AdapterInformationBinding) :
       VerticalListViewHolder(binding) {
        fun bind(information: Information) {
            binding.apply {

                title.text=information.title
                description.text=information.description
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Information>() {
        override fun areItemsTheSame(oldItem: Information, newItem: Information) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Information, newItem: Information) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}