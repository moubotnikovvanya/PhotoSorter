package com.example.photosorter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.photosorter.databinding.RecycleviewItemImageBinding

class AdapterImagesGrid(val images:List<DocumentFile>):RecyclerView.Adapter<AdapterImagesGrid.MyViewHolder>(){

    class MyViewHolder(val binding: RecycleviewItemImageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecycleviewItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        Log.i("DATA_IMAGES",images.toString())
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = images[position]
        holder.binding.image.load(image.uri) {
            crossfade(true) // Плавное появление
            placeholder(android.R.drawable.ic_menu_gallery) // Заглушка во время загрузки
            error(android.R.drawable.ic_delete) // Иконка при ошибке чтения
        }
    }
}