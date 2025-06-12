package com.example.dtasks.view.list

import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class AddTaskFragment : Fragment() {

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<AddTaskViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ListActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.taskDateTIET.apply {
            isFocusable = false
            isClickable = true
        }

        binding.addButton.setOnClickListener {
            val id = UUID.randomUUID().toString()

            viewModel.createTaskInfo(id,
                binding.taskNameTIET.text.toString(),
                binding.taskDescriptionTIET.text.toString(),
                format.parse(binding.taskDateTIET.text.toString()) ?: Date())
        }

        binding.taskDateTIET.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(),{ _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.taskDateTIET.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
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