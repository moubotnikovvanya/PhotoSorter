package com.example.photosorter

import android.content.Context
import android.icu.text.Transliterator.Position
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photosorter.databinding.FragmentImagesGridBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext


class ImagesGridFragment : Fragment() {
    private var _binding: FragmentImagesGridBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImagesGridBinding.inflate(inflater,container,false)
        val viewModel: FragmentCommunicator by activityViewModels()



        val transitionInterface:TransitionBetweenFragments = object : TransitionBetweenFragments {
            override fun openImage(images: List<DocumentFile>,position:Int) {
                viewModel.images = images
                viewModel.position = position
                findNavController().navigate(R.id.action_imagesGridFragment_to_imageShowcaseFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val uri = viewModel.uri ?: return@launch

            val list_images = withContext(Dispatchers.IO){
                getImagesFromFolder(requireContext().applicationContext, uri)
            }
            Log.i("DATA_IMAGES", list_images.toString())

            binding.recycleviewImagesGrid.layoutManager = GridLayoutManager(context,2)
            val adapter = AdapterImagesGrid(list_images,transitionInterface)
            binding.recycleviewImagesGrid.adapter = adapter
        }





        return binding.root //inflater.inflate(R.layout.fragment_images_grid, container, false)
    }


    fun getImagesFromFolder(context: Context, folderUri: Uri): List<DocumentFile> {
        val rootFolder = DocumentFile.fromTreeUri(context, folderUri) ?: return emptyList()

        return rootFolder.listFiles().filter { file ->
            if (!file.isFile) return@filter false

            // First check
            val mimeType = file.type
            if (mimeType != null && mimeType.startsWith("image/")) {
                return@filter true
            }else
                return@filter false
        //        val imageExtensions = setOf("jpg", "jpeg", "png", "gif", "webp", "bmp", "heic", "heif")
//            val extension = file.name?.substringAfterLast('.', "")?.lowercase()
//            extension in imageExtensions
        }
    }

    interface TransitionBetweenFragments{
        fun openImage(images:List<DocumentFile>,position: Int)
    }
}