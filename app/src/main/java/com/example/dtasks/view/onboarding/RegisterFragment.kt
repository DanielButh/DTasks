package com.example.dtasks.view.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dtasks.R
import com.example.dtasks.databinding.FragmentRegisterBinding
import com.example.dtasks.utils.FragmentCommunicator
import com.example.staysunny.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<RegisterViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        setupObservers()
        return binding.root

    }

    private fun setupView() {
        binding.backIB.setOnClickListener {
            findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
        }

        binding.registerButton.setOnClickListener {
            requestRegister()
        }

    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
        viewModel.createdUser.observe(viewLifecycleOwner) { createdUser ->
            if (createdUser) {
                findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
            }
        }
    }

    private fun requestRegister() {
        if (binding.emailTIET.text.toString().isNotEmpty()
            && binding.passwordTIET.text.toString().isNotEmpty()) {
            isValid = true
        } else {
            isValid = false
        }

        if (isValid) {
            viewModel.requestRegister(binding.emailTIET.text.toString(),
                binding.passwordTIET.text.toString()
            )
        } else {
            Toast.makeText(activity, "Please introduce your information", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}