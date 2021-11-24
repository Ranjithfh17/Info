package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.callbacks.InfoOptionsListener
import com.fh.info.data.model.InfoOptions
import com.fh.info.databinding.AdapterOptionsBinding
import com.fh.info.databinding.AdpterAppInfoOptionsBinding

class AdapterInfoOptions(private val infoOptionsListener: InfoOptionsListener) : RecyclerView.Adapter<AdapterInfoOptions.InfoOptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoOptionViewHolder {
        val binding =
            AdpterAppInfoOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InfoOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoOptionViewHolder, position: Int) {
        val infoOptions = differ.currentList[position]
        if (infoOptions != null) {
            holder.bind(infoOptions)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

   inner class InfoOptionViewHolder(private val binding: AdpterAppInfoOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(infoOptions: InfoOptions) {
            binding.apply {
                Glide.with(itemView)
                    .load(infoOptions.infoOptionIcon)
                    .into(infoIcon)

                infoName.text=infoOptions.infoOptionName
            }
        }

        init {
            binding.infoName.isSelected=true

            binding.root.setOnClickListener{
                val position=bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    infoOptionsListener.onInfoOptionClick(differ.currentList[position])
                }
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<InfoOptions>() {
        override fun areItemsTheSame(oldItem: InfoOptions, newItem: InfoOptions) =
            oldItem.infoOptionName == newItem.infoOptionName

        override fun areContentsTheSame(oldItem: InfoOptions, newItem: InfoOptions) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}