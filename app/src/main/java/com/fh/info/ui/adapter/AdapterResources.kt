package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.databinding.AdapterExtrasBinding
import com.fh.info.databinding.AdapterResourcesBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterResources() : RecyclerView.Adapter<AdapterResources.ResourcesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
        val binding =
            AdapterResourcesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResourcesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResourcesViewHolder, position: Int) {
        val component = differ.currentList[position]
        if (component != null) {
            holder.bind(component)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ResourcesViewHolder(private val binding: AdapterResourcesBinding) :
        VerticalListViewHolder(binding) {
        fun bind(extra: String) {
            binding.apply {
                resourcesName.text = extra
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}