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
import com.fh.info.databinding.AdapterAppInfoItemsBinding
import com.fh.info.databinding.AdpterAppInfoOptionsBinding

class AdapterInfoItems(private val infoItemListener: InfoItemListener) : RecyclerView.Adapter<AdapterInfoItems.InfoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoItemViewHolder {
        val binding =
            AdapterAppInfoItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoItemViewHolder, position: Int) {
        val infoOptions = differ.currentList[position]
        if (infoOptions != null) {
            holder.bind(infoOptions)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

   inner class InfoItemViewHolder(private val binding: AdapterAppInfoItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(infoItems: InfoItems) {
            binding.apply {
                Glide.with(itemView)
                    .load(infoItems.icon)
                    .into(infoIcon)
                infoName.text=infoItems.infoName
            }
        }

        init {
            binding.infoName.isSelected=true

            binding.root.setOnClickListener{
                val position=bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    infoItemListener.onInfoItemClick(differ.currentList[position])
                }
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<InfoItems>() {
        override fun areItemsTheSame(oldItem: InfoItems, newItem: InfoItems) =
            oldItem.infoName == newItem.infoName

        override fun areContentsTheSame(oldItem: InfoItems, newItem: InfoItems) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}