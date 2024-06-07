package ru.shuevalov.metronome_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.shuevalov.metronome_project.R
import ru.shuevalov.metronome_project.databinding.AccountSettingsFragmentBinding

class AccountSettingsFragment : Fragment() {

    private lateinit var binding: AccountSettingsFragmentBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountSettingsFragmentBinding.inflate(inflater)
        auth = Firebase.auth

        binding.changeNickname.setOnClickListener {
            val editText = EditText(activity)
            MaterialAlertDialogBuilder(requireContext())
                .setView(editText)
                .setTitle("enter a new nickname")
                .setNegativeButton("cancel") { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton("save") { dialog, which ->
                    val nicknameUpdate = UserProfileChangeRequest.Builder()
                        .setDisplayName(editText.text.toString())
                        .build()
                    auth.currentUser?.updateProfile(nicknameUpdate)
                }
                .show()
        }

        binding.logOut.setOnClickListener {
            auth.signOut()
            toast("sign out")
            parentFragmentManager.popBackStackImmediate()
        }

        binding.deleteAccount.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("delete account?")
                .setNegativeButton("cancel") { dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton("delete") { dialog, which ->
                    auth.currentUser?.delete()
                    toast("account is deleted")
                    parentFragmentManager.popBackStackImmediate()
                }
                .show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar?.apply {
            setTitle(context.getString(R.string.account_settings))
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