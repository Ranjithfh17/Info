package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.R
import com.fh.info.data.model.UsesFeatures
import com.fh.info.databinding.AdapterFeatureBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterFeatures() : RecyclerView.Adapter<AdapterFeatures.FeaturesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturesViewHolder {
        val binding =
            AdapterFeatureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeaturesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeaturesViewHolder, position: Int) {
        val feature = differ.currentList[position]
        if (feature != null) {
            holder.bind(feature)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class FeaturesViewHolder(private val binding: AdapterFeatureBinding) :
        VerticalListViewHolder(binding) {
        fun bind(feature: UsesFeatures) {
            binding.apply {
                featureName.text = feature.name
                featureStatus.text = if (feature.isRequired) {
                    itemView.context.getString(R.string.required)
                }else{
                    itemView.context.getString(R.string.not_required)

                }


            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<UsesFeatures>() {
        override fun areItemsTheSame(oldItem: UsesFeatures, newItem: UsesFeatures) =
            oldItem.name == newItem.name

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: UsesFeatures, newItem: UsesFeatures) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}