package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.R
import com.fh.info.databinding.AdapterDexBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterDex() : RecyclerView.Adapter<AdapterDex.DexViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DexViewHolder {
        val binding =
            AdapterDexBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DexViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DexViewHolder, position: Int) {
        val dex = differ.currentList[position]
        if (dex != null) {
            holder.bind(dex)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class DexViewHolder(private val binding: AdapterDexBinding) :
        VerticalListViewHolder(binding) {
        fun bind(dex: net.dongliu.apk.parser.bean.DexClass) {
            binding.apply {
                dexTitle.text = dex.classType
                packageName.text = dex.packageName
                superClass.text = dex.superClass



                if (dex.isPublic) {
                    stat.text = itemView.context.getString(R.string.public_text)
                } else {
                    stat.text = itemView.context.getString(R.string.private_text)

                }

                if (dex.isProtected) {
                    stat.text = itemView.context.getString(R.string.private_text)

                }
                if (dex.isStatic) {
                    stat.text = itemView.context.getString(R.string.static_text)

                }
                if (dex.isInterface) {
                    stat.text = itemView.context.getString(R.string.interface_text)

                }
                if (dex.isEnum) {
                    stat.text = itemView.context.getString(R.string.enum_text)

                }
                if (dex.isAnnotation) {
                    stat.text = itemView.context.getString(R.string.annotation_text)

                }
                if (stat.text.toString().isEmpty()) {
                    itemView.context.getString(R.string.not_found)
                }

            }
        }

    }

    private val differCallback =
        object : DiffUtil.ItemCallback<net.dongliu.apk.parser.bean.DexClass>() {
            override fun areItemsTheSame(
                oldItem: net.dongliu.apk.parser.bean.DexClass,
                newItem: net.dongliu.apk.parser.bean.DexClass
            ) =
                oldItem.packageName == newItem.packageName

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: net.dongliu.apk.parser.bean.DexClass,
                newItem: net.dongliu.apk.parser.bean.DexClass
            ) =
                oldItem == newItem
        }

    val differ = AsyncListDiffer(this, differCallback)
}