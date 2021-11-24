package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.R
import com.fh.info.databinding.AdapterReceiversBinding
import com.fh.info.decor.view.VerticalListViewHolder
import com.jaredrummler.apkparser.model.AndroidComponent

class AdapterReceiver() : RecyclerView.Adapter<AdapterReceiver.ReceiverViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiverViewHolder {
        val binding =
            AdapterReceiversBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceiverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceiverViewHolder, position: Int) {
        val component = differ.currentList[position]
        if (component != null) {
            holder.bind(component)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ReceiverViewHolder(private val binding: AdapterReceiversBinding) :
        VerticalListViewHolder(binding) {
        fun bind(component: AndroidComponent) {
            binding.apply {
                receiverName.text = component.name.substring(component.name.lastIndexOf(".") + 1)
                receiverStatus.text = if (component.exported) {
                    itemView.context.getString(R.string.exported)
                } else {
                    itemView.context.getString(R.string.not_exported)

                }

                receiverPackage.text=component.name

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