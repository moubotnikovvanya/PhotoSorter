package com.example.photosorter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.RecyclerView
import com.example.photosorter.databinding.ItemImageShowcaseBinding
import com.example.photosorter.databinding.RecycleviewItemImageBinding

class AdapterViewPage(private val images:List<DocumentFile>):
    RecyclerView.Adapter<AdapterViewPage.ImageViewHolder>() {

    class ImageViewHolder(val binding: ItemImageShowcaseBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageShowcaseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.binding.imageView.setImageURI(image.uri)
    }

}