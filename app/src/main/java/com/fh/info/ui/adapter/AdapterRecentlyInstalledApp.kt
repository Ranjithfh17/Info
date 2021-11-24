package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.R
import com.fh.info.databinding.AdapterActivitiesBinding
import com.fh.info.databinding.AdapterRecentlyInstalledAppBinding
import com.fh.info.decor.view.HorizontalListViewViewHolder
import com.fh.info.decor.view.VerticalListViewHolder
import com.jaredrummler.apkparser.model.AndroidComponent

class AdapterRecentlyInstalledApp : RecyclerView.Adapter<AdapterRecentlyInstalledApp.RecentAppViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentAppViewHolder {
        val binding =
            AdapterRecentlyInstalledAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentAppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentAppViewHolder, position: Int) {
        val component = differ.currentList[position]
        if (component != null) {
            holder.bind(component)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size/5
    }

    inner class RecentAppViewHolder(private val binding: AdapterRecentlyInstalledAppBinding) :
        HorizontalListViewViewHolder(binding) {
        fun bind(component: PackageInfo) {
            binding.apply {
                Glide.with(itemView)
                    .asDrawable()
                    .load(itemView.context.packageManager.getApplicationIcon(component.applicationInfo.packageName))
                    .into(appIcon)

                appName.text=component.applicationInfo.name

            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<PackageInfo>() {
        override fun areItemsTheSame(oldItem: PackageInfo, newItem: PackageInfo) =
            oldItem.applicationInfo.name == newItem.applicationInfo.name

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PackageInfo, newItem: PackageInfo) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}