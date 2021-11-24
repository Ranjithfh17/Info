package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.callbacks.OptionsItemListener
import com.fh.info.data.model.Options
import com.fh.info.databinding.AdapterOptionsBinding

class AdapterOptions(private val listener:OptionsItemListener) :RecyclerView.Adapter<AdapterOptions.AdapterOptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOptionViewHolder {
        val binding=AdapterOptionsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterOptionViewHolder, position: Int) {
        val options= differ.currentList[position]
        if (options != null){
            holder.bind(options)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


   inner class AdapterOptionViewHolder(private val binding: AdapterOptionsBinding) :RecyclerView.ViewHolder(binding.root) {

        fun bind(options: Options){
            binding.apply {
                Glide.with(itemView)
                    .load(options.optionIcon)
                    .into(optionIcon)
                optionText.text=options.optionName


            }

        }

        init {
            binding.optionText.isSelected=true

            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onOptionItemClick(differ.currentList[position])
                }
            }
        }

    }

    private val differCallback=object :DiffUtil.ItemCallback<Options>(){
        override fun areItemsTheSame(oldItem: Options, newItem: Options) =
            oldItem.optionName == newItem.optionName

        override fun areContentsTheSame(oldItem: Options, newItem: Options) =
            oldItem == newItem
    }

    val differ=AsyncListDiffer(this,differCallback)
}