package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.R
import com.fh.info.databinding.AdapterActivitiesBinding
import com.fh.info.databinding.AdapterServicesBinding
import com.fh.info.decor.view.VerticalListViewHolder
import com.jaredrummler.apkparser.model.AndroidComponent

class AdapterService() : RecyclerView.Adapter<AdapterService.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding =
            AdapterServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val component = differ.currentList[position]
        if (component != null) {
            holder.bind(component)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ServiceViewHolder(private val binding: AdapterServicesBinding) :
        VerticalListViewHolder(binding) {
        fun bind(component: AndroidComponent) {
            binding.apply {
                serviceName.text = component.name.substring(component.name.lastIndexOf(".") + 1)
                serviceStatus.text = component.name
                servicePackage.text = if (component.exported) {
                    itemView.context.getString(R.string.exported)
                } else {
                    itemView.context.getString(R.string.not_exported)
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