package com.info.firebaseauth.screens.authentication

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.info.firebaseauth.R
import com.info.firebaseauth.databinding.RegistrationBinding
import com.info.firebaseauth.remote.RemoteAuth

import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit


class Registration : Fragment() {

    companion object{
        const val TAG = "RegistrationFragment"
        const val OTP_KEY = "OTP_Code"
    }
    private lateinit var binding: RegistrationBinding

    private val viewModel by sharedViewModel<AuthViewModel>()

    private var _emailPhone: String = ""
    private var _password: String = ""


    lateinit var storedVerificationId:String
    lateinit var resendToken: ForceResendingToken
    private lateinit var callbacks: OnVerificationStateChangedCallbacks


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.registration,container,false)


        setViews()
        setObserves()

        return binding.root
    }

    private fun setObserves() {

        binding.apply {

            authViewModel = viewModel
            lifecycleOwner = this@Registration


            /** login live data **/
            viewModel.isRegister.observe(viewLifecycleOwner) { user ->
                if (user != null) {
//                    viewModel.signUpIsDone()
                }
            }



        }

    }

    private fun setViews() {
        binding.apply {

            /** button sign up **/
            btnSignUp.setOnClickListener {
                signUp()
            }

            /** login button **/
            btnSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_registration_to_login)
            }


            /** button switch **/
            btnSwitch.setOnClickListener {
                viewModel.switchTo()
            }


            // Callback function for Phone Auth
            callbacks = object : OnVerificationStateChangedCallbacks() {

                // This method is called when the verification is completed
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // go to main screen
                    Log.d(TAG, "onVerificationCompleted Success")
                }

                // Called when verification is failed add log statement to see the exception
                override fun onVerificationFailed(e: FirebaseException) {
                    Log.d(TAG, "onVerificationFailed  $e")
                }

                // On code is sent by the firebase this method is called
                // in here we start a new activity where user can enter the OTP
                override fun onCodeSent(
                    verificationId: String,
                    token:ForceResendingToken
                ) {
                    Log.d(TAG,"onCodeSent: $verificationId")

                resendToken = token

                val data = bundleOf(OTP_KEY to verificationId)
                findNavController().navigate(R.id.action_registration_to_OTP,data)

                }
            }


        }
    }



    private fun signUp() {
        _emailPhone = binding.emailPhone.text.trim().toString()
        _password = binding.password.text.trim().toString()

        if (_emailPhone.isNotBlank() && _password.isNotBlank()){
            when(viewModel.isByPhone.value){
                true ->{
                    Log.d(TAG, "SignUp by phone")
                    val countryCode = "+218"
                    val number = countryCode + _emailPhone
                    sendVerificationCode(number)
                }
                false ->{
                    Log.d(TAG, "SignUp by email")
                    viewModel.signUpByEmail(_emailPhone,_password)
                }
            }
        }else{
            Toast.makeText(requireContext(),"fill information",Toast.LENGTH_LONG).show()
        }
    }





     private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(RemoteAuth().auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d(TAG, "Verification Code has been sent ..")
    }




}