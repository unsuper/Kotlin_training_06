package com.training.kotlin_training_06

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.training.kotlin_training_06.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val TAG = FirstFragment::class.qualifiedName
    private var _binding: FragmentFirstBinding ?= null
    private val binding get() = _binding!!
    private var initCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCount.setOnClickListener {
            initCount += 1
            binding.textviewFirst.text = initCount.toString()
        }
        
        val shared: SharedPreferences = requireContext().getSharedPreferences(COUNT, Context.MODE_PRIVATE)

        binding.buttonFirst.setOnClickListener {
            shared.edit {
                this.putInt(COUNT, initCount)
            }
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getInt(COUNT)?.let {
            initCount = savedInstanceState.getInt(COUNT) * 10
            binding.textviewFirst.text = initCount.toString()
        }
    }

    companion object {
        const val COUNT = "COUNT"
        fun newInstance(count: Int) : FirstFragment{
           val fragment = FirstFragment()
           val bundle = Bundle().apply {
               putInt(COUNT, count)
           }
            fragment.arguments = bundle
            return fragment
        }
    }

    //Saved state in this callback
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COUNT, initCount) // Push state
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}