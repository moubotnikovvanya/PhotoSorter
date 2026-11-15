package com.example.photosorter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.photosorter.databinding.FragmentImageShowcaseBinding

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
}