package com.excample.noteapp.fragments.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.excample.noteapp.R
import com.excample.noteapp.databinding.FragmentSignUpBinding
import com.excample.noteapp.utils.PreferenceHelper
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private var auth: FirebaseAuth? = null
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accessCodeButton.isVisible = false
        PreferenceHelper.unit(requireContext())
        setupListener()
    }

    private fun setupListener() = with(binding) {
        sendCodeButton.setOnClickListener {
            startPhoneNumberVerification(etPhone.text.toString())
            sendCodeButton.isVisible = false
            accessCodeButton.isVisible = true
        }
        accessCodeButton.setOnClickListener {
            verifyPhoneNumberWithCode(storedVerificationId, etCode.text.toString())
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber)     //Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS)   //Timeout unit
            .setActivity(requireActivity())      //Activity (for callback binding)
            .setCallbacks(callbacks)       //OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneNumberAuthCredential(credential)
    }

    private var callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneNumberAuthCredential(credential)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Log.e("tag", "error")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneNumberAuthCredential(credential: PhoneAuthCredential) {
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    PreferenceHelper.signUp = true
                    // Sign in success , update UI with the signed - in user's information
                    findNavController().navigate(R.id.noteAppFragment)
                } else {
                    //Sign in failed , display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "Registration is not", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }
}


