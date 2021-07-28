package com.example.encryption

import android.graphics.Color.RED
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.encryption.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {
     val viewModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
   // private val binding = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

setHasOptionsMenu(true)



        binding.btnGenerate.setOnClickListener {
            binding.textBox.addTextChangedListener{
                if (it.toString().length > 0){
                    binding.textBox.setBackgroundResource(R.drawable.text_box_bg_empty)
                }else{
                    binding.textBox.setBackgroundResource(R.drawable.text_box_bg)
                }
            }
        authenticateInput()

        }


        return binding.root
    }

    private fun authenticateInput() {
        if (binding.textBox.text.isNullOrEmpty()){
            binding.textBox.setBackgroundResource(R.drawable.text_box_bg)
            binding.textBox.error = "Text cannot be empty"
        }else{

            lifecycleScope.launch {

                binding.textBox.setBackgroundResource(R.drawable.text_box_bg_empty)
                val plainText = binding.textBox.text.toString()
                val algorithm = binding.autoCompleteTv.text.toString()
               val result = viewModel.hash(plainText,algorithm)
                Log.d("HomeFragment",result)
                applyAnimation()
                navigate(result)           }

        }
  //     binding.textBox.addTextChangedListener{
  //         if (it.toString().length > 0){
  //             binding.textBox.setBackgroundResource(R.drawable.text_box_bg_empty)
  //         }else{
  //
  //         }
  //     }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_menu){
            binding.textBox.text = null
            Snackbar.make(binding.root,"Cleared",Snackbar.LENGTH_SHORT).show()
            return true
        }
        return true
    }
    private fun navigate(result:String) {
        val directions = HomeFragmentDirections.actionHomeFragmentToSuccessFragment(result)
         findNavController().navigate(directions)
    }

    private suspend fun applyAnimation() {
        binding.apply {
            btnGenerate.isClickable = false
            hashTitle.animate().alpha(0f).duration =400L
            btnGenerate.animate().alpha(0f).duration =400L
            textInputLayout.animate().alpha(0f).translationXBy(1200f).duration =400L
            textBox.animate().alpha(0f).translationXBy(-1200f).duration =400L
    delay(300)
            successView.animate().alpha(1f).duration =600L
            successView.animate().rotationBy(720f).duration =600L
            successView.animate().scaleXBy(900f).duration =800L
            successView.animate().scaleYBy(900f).duration =800L

            delay(500)
            successImage.animate().alpha(1f).duration =1000L
            delay(1500)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onResume() {
        super.onResume()
        val algorithms = resources.getStringArray(R.array.hash_algorithms)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.layout_view,algorithms)
        binding.autoCompleteTv.setAdapter(arrayAdapter)
        binding.textBox.text = null

    }

    override fun onDestroyView() {
        super.onDestroyView()
      //  binding = null
    }

}