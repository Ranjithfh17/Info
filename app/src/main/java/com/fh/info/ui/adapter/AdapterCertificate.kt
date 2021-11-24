package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fh.info.data.model.CertificateModel
import com.fh.info.databinding.AdapterCertificateBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterCertificate() : RecyclerView.Adapter<AdapterCertificate.CertificateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val binding =
            AdapterCertificateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CertificateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val certificate = differ.currentList[position]
        if (certificate != null) {
            holder.bind(certificate)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CertificateViewHolder(private val binding: AdapterCertificateBinding) :
        VerticalListViewHolder(binding) {
        fun bind(certificate: CertificateModel) {
            binding.apply {

                certificateTitle.text = certificate.title
                certificateData.text = certificate.description
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<CertificateModel>() {
        override fun areItemsTheSame(oldItem: CertificateModel, newItem: CertificateModel) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: CertificateModel, newItem: CertificateModel) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}