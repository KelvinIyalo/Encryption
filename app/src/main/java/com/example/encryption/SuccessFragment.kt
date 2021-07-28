package com.example.encryption

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.encryption.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SuccessFragment : Fragment(R.layout.fragment_success) {
    private val args:SuccessFragmentArgs by navArgs()
    lateinit var binding: FragmentSuccessBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSuccessBinding.inflate(layoutInflater)


        Log.d("success",args.hash)
        binding.hashText.text = args.hash
    binding.btnCopy.setOnClickListener {
        onCopyClicked()
    }


    return binding.root}

    private fun onCopyClicked() {
        lifecycleScope.launch {
            animate()
        }
         copyToClipBoard(args.hash)
    }

    private fun copyToClipBoard(hash:String) {
         val clipboardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text",hash)
        clipboardManager.setPrimaryClip(clipData)
    }
    private suspend fun animate(){
        binding.apply {
            include.textBg.animate().translationY(80f).duration = 200L
            include.textView.animate().translationY(80f).duration = 200L
            delay(2000)
            include.textBg.animate().translationY(-80f).duration = 500L
            include.textView.animate().translationY(-80f).duration = 500L

        }
    }
}