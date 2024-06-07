package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.AuthenticationFragmentBinding

class AuthenticationFragment : Fragment() {
    private lateinit var binding: AuthenticationFragmentBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthenticationFragmentBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() or password.isEmpty()) {
                toast("Field is empty")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("Sign in success")
//                    findNavController().navigate(R.id.action_authenticationFragment_to_settingsFragment)
//                    activity?.onBackPressedDispatcher?.dispatchOnBackStarted()
                    findNavController().navigate(
                        R.id.action_authenticationFragment_to_settingsFragment,
                        arguments,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.settingsFragment, true)
                            .build()
                    )
                }
                else
                    toast("Sign in failed")
            }
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar?.apply {
            setTitle(context.getString(R.string.sign_in))
            setNavigationIcon(R.drawable.ic_back)
            setNavigationOnClickListener {
                parentFragmentManager.popBackStackImmediate()
            }
        }
    }
    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}