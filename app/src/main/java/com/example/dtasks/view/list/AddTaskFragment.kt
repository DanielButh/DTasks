package com.example.dtasks.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dtasks.R
import com.example.dtasks.databinding.FragmentAddTaskBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddTaskFragment_to_TasksFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}