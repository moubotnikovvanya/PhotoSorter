package com.example.photosorter.fragments

import android.content.Context
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.photosorter.adapters.AdapterViewPage
import com.example.photosorter.FragmentCommunicator
import com.example.photosorter.R
import com.example.photosorter.databinding.FragmentImageShowcaseBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageShowcaseFragment: Fragment() {

    var _binding:FragmentImageShowcaseBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImageShowcaseBinding.inflate(inflater,container,false)

        val viewModel: FragmentCommunicator by activityViewModels()

        val adapter = AdapterViewPage(viewModel.images!!)
        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(viewModel.position!!,false)



//        binding.buttonMove.setOnClickListener {
//            val position = binding.viewPager.currentItem
//            val currentFile = adapter.getFileAt(position) ?: return@setOnClickListener
//
//            viewLifecycleOwner.lifecycleScope.launch {
//                val isMoved = withContext(Dispatchers.IO) {
//                    moveDocumentFile(
//                        requireContext(),
//                        currentFile,
//                        DocumentFile.fromTreeUri(requireContext(), viewModel.uri!!)!!,
//                        targetDir
//                    )
//                }
//                if (isMoved) {
//                    adapter.removeAt(position)
//                    // ViewPager2 автоматически переключится на соседний элемент
//                }
//            }
//        }

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.inflateMenu(R.menu.toolbar_image_actions)

        binding.toolbar.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.copy -> {
                    true
                }
                R.id.delete -> {
                    true
                }
                else -> false
            }
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }



    fun moveDocumentFile(context: Context,sourceFile: DocumentFile,sourceParent: DocumentFile,targetParent: DocumentFile): Boolean {
        return try {
            val movedUri = DocumentsContract.moveDocument(
                context.contentResolver,
                sourceFile.uri,
                sourceParent.uri,
                targetParent.uri
            )
            movedUri != null
        } catch (e: Exception) {
            false
        }
    }



}