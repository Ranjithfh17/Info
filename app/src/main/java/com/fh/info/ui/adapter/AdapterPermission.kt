package com.fh.info.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.R
import com.fh.info.data.model.PermissionInfo
import com.fh.info.databinding.AdapterPermissionsBinding
import com.fh.info.decor.view.VerticalListViewHolder
import com.fh.info.utils.PermissionUtils.getPermissionInfo
import com.fh.info.utils.PermissionUtils.protectionToString

class AdapterPermission() : RecyclerView.Adapter<AdapterPermission.PermissionViewHolder>() {

    private var permissionInfo: android.content.pm.PermissionInfo? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
        val binding =
            AdapterPermissionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PermissionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        val perm = differ.currentList[position]
        if (perm != null) {
            holder.bind(perm)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class PermissionViewHolder(private val binding: AdapterPermissionsBinding) :
        VerticalListViewHolder(binding) {
        @RequiresApi(Build.VERSION_CODES.P)
        fun bind(perm: PermissionInfo) {

            permissionInfo = perm.permissionName.getPermissionInfo(itemView.context)
            binding.apply {
                permissionName.text = perm.permissionName

                var text = protectionToString(
                    permissionInfo!!.protection,
                    permissionInfo!!.protectionFlags,
                    itemView.context
                )
                text = if (perm.granted) {
                    "$text | Granted"
                } else {
                    "$text | Rejected"
                }

                permissionStatus.text = text

                val desc = permissionInfo!!.loadDescription(itemView.context.packageManager)
                if (desc.isNullOrEmpty()) {
                   permissionDescription.text=itemView.context.getString(R.string.perm_desc)
                }else{
                    permissionDescription.text =desc

                }


            }
        }

    }


    private val differCallback = object : DiffUtil.ItemCallback<PermissionInfo>() {
        override fun areItemsTheSame(oldItem: PermissionInfo, newItem: PermissionInfo) =
            oldItem.permissionName == newItem.permissionName

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: PermissionInfo, newItem: PermissionInfo) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}