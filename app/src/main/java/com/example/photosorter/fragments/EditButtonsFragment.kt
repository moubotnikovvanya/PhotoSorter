package com.example.photosorter.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.photosorter.ButtonSettings
import com.example.photosorter.FragmentCommunicator
import com.example.photosorter.R
import com.example.photosorter.databinding.DialogAddButtonBinding
import com.example.photosorter.databinding.FragmentSetUpButtonsBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditButtonsFragment:Fragment() {

    var _binding:FragmentSetUpButtonsBinding? = null
    val binding get() = _binding!!

    var choosenUri:Uri? = null
    var list_buttons:MutableList<ButtonSettings> = mutableListOf<ButtonSettings>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSetUpButtonsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarEditButtons.inflateMenu(R.menu.toolbar_edit_buttons_actions)

        binding.toolbarEditButtons.setOnMenuItemClickListener { menu ->
            when(menu.itemId){
                R.id.add -> {
                    addDialog(requireContext())
                    true
                }
                R.id.delete -> {
                    true
                }
                else -> false
            }
        }
        binding.toolbarEditButtons.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

    fun addDialog(context: Context) {
        val main_dialog = Dialog(context)
        val dialog_binding = DialogAddButtonBinding.inflate(LayoutInflater.from(context))

        main_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        main_dialog.setContentView(dialog_binding.root)
        main_dialog.setCancelable(true)
        main_dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog_binding.buttonChooseDirectory.setOnClickListener(){
            openDirectoryLauncher.launch(null)
        }
        val title = dialog_binding.editTextButtonName.text
        dialog_binding.buttonSaveSettings.setOnClickListener(){
            if (choosenUri != null){

                list_buttons?.add(
                    ButtonSettings(
                        title = title.toString(),
                        targetDir = DocumentFile.fromTreeUri(requireContext(), choosenUri!!)!!
                    )
                )
                main_dialog.dismiss()
                renderButtons()
            }
        }

        main_dialog.show()
        choosenUri = null
    }

    private fun renderButtons() {
        binding.linearLayoutButtonsLeft.removeAllViews()
        list_buttons?.forEach { param ->
            val button = MaterialButton(requireContext()).apply {
                text = param.title
                setOnClickListener {
                    Toast.makeText(requireContext(),param.targetDir.name,Toast.LENGTH_LONG).show()  // ..........................
                }
            }
            binding.linearLayoutButtonsLeft.addView(button)
        }
    }

    private val openDirectoryLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri != null) {
            Log.i("PATH",uri.toString())
            try {
                choosenUri = uri
            }catch (e:Exception){
                Log.i("FOLDER_ERROR",e.toString())
            }


        }
    }


}