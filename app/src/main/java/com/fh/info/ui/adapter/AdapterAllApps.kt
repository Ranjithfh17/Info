package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.R
import com.fh.info.callbacks.AppInfoListener
import com.fh.info.databinding.AdapterAllAppsBinding
import com.fh.info.decor.view.VerticalListViewHolder
import com.fh.info.utils.FileHelper.getFileSize

class AdapterAllApps(private val appInfoListener:AppInfoListener) : RecyclerView.Adapter<AdapterAllApps.AllAppsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAppsViewHolder {
        val binding =
            AdapterAllAppsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllAppsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllAppsViewHolder, position: Int) {
        val applicationInfo = differ.currentList[position]
        if (applicationInfo != null) {
            holder.bind(applicationInfo)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

   inner class AllAppsViewHolder(private val binding: AdapterAllAppsBinding) :
        VerticalListViewHolder(binding) {
        fun bind(applicationInfo: ApplicationInfo) {
            binding.apply {
                Glide.with(itemView)
                    .asDrawable()
                    .load(itemView.context.packageManager.getApplicationIcon(applicationInfo.packageName))
                    .into(appIcon)

                appName.text = applicationInfo.name
                packageName.text = applicationInfo.packageName
                appSize.text = applicationInfo.sourceDir.getFileSize()
                appType.text = if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                    itemView.context.getString(R.string.user)
                } else {
                    itemView.context.getString(R.string.system)
                }
            }

        }

        init {
            binding.root.setOnClickListener {
                val position=bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    appInfoListener.onInfoClick(differ.currentList[position])
                }
            }
        }


    }

    private val differCallback = object : DiffUtil.ItemCallback<ApplicationInfo>() {
        override fun areItemsTheSame(oldItem: ApplicationInfo, newItem: ApplicationInfo) =
            oldItem.name == newItem.name

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ApplicationInfo, newItem: ApplicationInfo) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}