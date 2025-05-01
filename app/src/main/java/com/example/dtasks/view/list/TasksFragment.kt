 package com.example.dtasks.view.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dtasks.R
import com.example.dtasks.databinding.FragmentTasksBinding
import com.example.dtasks.utils.FragmentCommunicator

 /**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator

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
        binding.AddButton.setOnClickListener {
            findNavController().navigate(R.id.action_TasksFragment_to_AddTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}