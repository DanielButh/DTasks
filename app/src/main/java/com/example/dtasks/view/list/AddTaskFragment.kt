package com.example.dtasks.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dtasks.R
import com.example.dtasks.databinding.FragmentAddTaskBinding
import com.example.dtasks.utils.FragmentCommunicator
import com.example.dtasks.viewModel.list.AddTaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import java.util.UUID

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<AddTaskViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.addButton.setOnClickListener {
            val id = UUID.randomUUID().toString()

            viewModel.createTaskInfo(id,
                binding.taskNameTIET.text.toString(),
                binding.taskDescriptionTIET.text.toString(),
                Date())
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_AddTaskFragment_to_TasksFragment)
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.operationSuccess.observe(viewLifecycleOwner) { operationSuccess ->
            if (operationSuccess) {
                findNavController().navigate(R.id.action_AddTaskFragment_to_TasksFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}