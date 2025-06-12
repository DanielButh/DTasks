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
import com.example.dtasks.databinding.FragmentTaskDetailBinding
import com.example.dtasks.model.Task
import com.example.dtasks.utils.FragmentCommunicator
import com.example.dtasks.viewModel.list.TaskDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<TaskDetailViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ListActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        val taskId = arguments?.let {
            TaskDetailFragmentArgs.fromBundle(it).taskId
        }
        viewModel.getTaskInfo(taskId?: "")

        setupObservers()

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_TaskDetailFragment_to_TasksFragment)
        }

        binding.taskDateTIET.apply {
            isFocusable = false
            isClickable = true
        }

        binding.saveButton.setOnClickListener {
            viewModel.updateTask(
                taskId?: "",
                binding.taskNameTIET.text.toString(),
                binding.taskDescriptionTIET.text.toString(),
                format.parse(binding.taskDateTIET.text.toString()) ?: Date())
        }

        binding.taskDateTIET.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Ajusta el mes (+1 porque empieza en 0)
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.taskDateTIET.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteTask(taskId?: "")

        }
    }

    private fun setupObservers() {
        viewModel.taskInfo.observe(viewLifecycleOwner) { task ->
            updateUI(task)
        }

        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.operationSuccess.observe(viewLifecycleOwner) { operationSuccess ->
            if (operationSuccess) {
                findNavController().navigate(R.id.action_TaskDetailFragment_to_TasksFragment)
            }
        }


    }

    private fun updateUI(task: Task) {
        binding.apply {
            taskNameTIET.setText(task.name)
            taskDescriptionTIET.setText(task.description)
            taskDateTIET.setText(task.date.toString())

        }
    }
}