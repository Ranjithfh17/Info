package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.R
import com.fh.info.databinding.AdapterActivitiesBinding
import com.fh.info.databinding.AdapterProvidersBinding
import com.fh.info.decor.view.VerticalListViewHolder
import com.jaredrummler.apkparser.model.AndroidComponent

class AdapterProviders() : RecyclerView.Adapter<AdapterProviders.ProvidersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvidersViewHolder {
        val binding =
            AdapterProvidersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProvidersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProvidersViewHolder, position: Int) {
        val component = differ.currentList[position]
        if (component != null) {
            holder.bind(component)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ProvidersViewHolder(private val binding: AdapterProvidersBinding) :
        VerticalListViewHolder(binding) {
        @SuppressLint("StringFormatMatches")
        fun bind(component: AndroidComponent) {
            binding.apply {
                providerName.text=component.name.substring(component.name.lastIndexOf(".") + 1)
                providerPackage.text=component.name
                providerStatus.text=

                    if (component.exported) {
                      "Exported"
                    } else {
                     "Not Exported"
                    }


                


            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<AndroidComponent>() {
        override fun areItemsTheSame(oldItem: AndroidComponent, newItem: AndroidComponent) =
            oldItem.name == newItem.name

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: AndroidComponent, newItem: AndroidComponent) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}