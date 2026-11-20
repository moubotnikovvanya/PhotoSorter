package com.example.photosorter.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.photosorter.FragmentCommunicator
import com.example.photosorter.R
import com.example.photosorter.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private val openDirectoryLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri != null) {
            Log.i("PATH",uri.toString())
            try {
                val viewModel: FragmentCommunicator by activityViewModels()
                viewModel.uri = uri
                findNavController().navigate(R.id.action_homeFragment_to_imagesGridFragment)
            }catch (e:Exception){
                Log.i("FOLDER_ERROR",e.toString())
            }


        }
    }


    private var _binding: FragmentHomeBinding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.buttonGetFolder.setOnClickListener() {
            openDirectoryLauncher.launch(null)
        }
        binding.buttonSetUp.setOnClickListener(){
            findNavController().navigate(R.id.action_homeFragment_to_editButtonsFragment)
        }

        return binding.root
    }
}