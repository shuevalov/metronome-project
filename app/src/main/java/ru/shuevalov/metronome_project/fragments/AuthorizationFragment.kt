package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.AuthorizationFragmentBinding

class AuthorizationFragment : Fragment() {
    private lateinit var binding: AuthorizationFragmentBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthorizationFragmentBinding.inflate(inflater, container, false)
        FirebaseApp.initializeApp(activity?.baseContext!!)
        auth = Firebase.auth

        binding.createAccountButton.setOnClickListener {
            if (
                binding.emailEditText.text.toString().isEmpty() or
                binding.passwordEditText.text.toString().isEmpty()
            ) {
                toast("Field is empty")
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Register success")
                    findNavController().navigate(R.id.action_authorizationFragment_to_settingsFragment)
                }
                else
                    toast("Register failed")
            }
        }

        // other buttons

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar?.apply {
            setTitle(context.getString(R.string.sign_up))
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
//                if (binding.emailEditText.isFocused) binding.emailEditText.clearFocus()
//                else
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
        binding.alreadyButton.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_authenticationFragment)
        }
    }
    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}