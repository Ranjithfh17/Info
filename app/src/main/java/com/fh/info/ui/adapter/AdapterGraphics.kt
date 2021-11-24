package com.fh.info.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fh.info.data.model.GraphicModel
import com.fh.info.databinding.AdapterGraphicsBinding
import com.fh.info.decor.view.VerticalListViewHolder

class AdapterGraphics() : RecyclerView.Adapter<AdapterGraphics.GraphicsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraphicsViewHolder {
        val binding =
            AdapterGraphicsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GraphicsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GraphicsViewHolder, position: Int) {
        val graphic = differ.currentList[position]
        if (graphic != null) {
            holder.bind(graphic)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class GraphicsViewHolder(private val binding: AdapterGraphicsBinding) :
        VerticalListViewHolder(binding) {
        fun bind(graphic: GraphicModel) {
            binding.apply {
                Glide.with(itemView)
                    .asDrawable()
                    .load(graphic.path)
                    .into(image)

                var res=""
                for (x in graphic.xml.indices){
                    res=graphic.xml[x]
                }
                xml.text=res


            }

        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<GraphicModel>() {
        override fun areItemsTheSame(oldItem: GraphicModel, newItem: GraphicModel) =
            oldItem.path == newItem.path

        override fun areContentsTheSame(oldItem: GraphicModel, newItem: GraphicModel) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}