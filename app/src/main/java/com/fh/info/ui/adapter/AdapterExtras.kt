package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.databinding.AdapterExtrasBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterExtras() : RecyclerView.Adapter<AdapterExtras.ExtrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtrasViewHolder {
        val binding =
            AdapterExtrasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExtrasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExtrasViewHolder, position: Int) {
        val component = differ.currentList[position]
        if (component != null) {
            holder.bind(component)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ExtrasViewHolder(private val binding: AdapterExtrasBinding) :
        VerticalListViewHolder(binding) {
        fun bind(extra: String) {
            binding.apply {
                extras.text = extra
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