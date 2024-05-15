package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.AuthorizationFragmentBinding

class AuthorizationFragment : Fragment() {
    private lateinit var binding: AuthorizationFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthorizationFragmentBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar?.apply {
            setTitle("sign up")
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
//                if (binding.emailEditText.isFocused) binding.emailEditText.clearFocus()
//                else
                findNavController().navigate(R.id.action_authorizationFragment_to_settingsFragment)
            }
        }
        binding.alreadyButton.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_authenticationFragment)
        }
    }
}