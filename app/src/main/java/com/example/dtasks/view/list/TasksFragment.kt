 package com.example.dtasks.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dtasks.R
import com.example.dtasks.adapters.TaskAdapter
import com.example.dtasks.databinding.FragmentTasksBinding
import com.example.dtasks.model.Task
import com.example.dtasks.utils.FragmentCommunicator
import com.example.dtasks.viewModel.list.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint

 @AndroidEntryPoint
class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<TasksViewModel>()
    private lateinit var communicator: FragmentCommunicator
    private lateinit var tasksAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        communicator = requireActivity() as ListActivity
        setupView()
        return binding.root

    }

    private fun setupView() {
        setupObservers()

        tasksAdapter = TaskAdapter(
            mutableListOf()
        ) { taskId ->
            val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId)
            findNavController().navigate(action)
        }

        binding.TasksRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tasksAdapter
        }

        viewModel.fetchAllTasks()

         binding.AddButton.setOnClickListener {
             findNavController().navigate(R.id.action_TasksFragment_to_AddTaskFragment)
         }
     }

     fun setupObservers() {
         viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
             communicator.showLoader(loaderState)
         }

         viewModel.taskInfo.observe(viewLifecycleOwner) { tasks ->
             updateUI(tasks)
         }
     }

     fun updateUI(tasks: List<Task>) {
         tasksAdapter.add(tasks)
     }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}