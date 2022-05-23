package com.info.firebaseauth.screens.authentication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.info.firebaseauth.R
import com.info.firebaseauth.databinding.OtpBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class OTP : Fragment() {

    private lateinit var binding: OtpBinding
    private lateinit var otp: String
    private val viewModel by sharedViewModel<AuthViewModel>()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        otp = arguments?.get(Registration.OTP_KEY) as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.otp,container,false)



        setViews()
        setObserves()





        return binding.root
    }

    private fun setObserves() {

        /** login live data **/
        viewModel.isLogin.observe(viewLifecycleOwner) {
            if (it != null){
                if (it) {
                    Toast.makeText(requireContext(),"Login Success",Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun setViews() {
        binding.apply {


            btnSend.setOnClickListener {
                sendCredential()
            }


        }
    }

    private fun sendCredential(){
        val otpCode = binding.otpCode.text.trim().toString()
        if (otpCode.isNotEmpty()){
            if(otpCode.length == 6){
                val credential = PhoneAuthProvider.getCredential(otp, otpCode)
                viewModel.signInWithPhone(requireActivity(),credential)
            }
        }else{
            Toast.makeText(requireContext(),"please enter otp code",Toast.LENGTH_SHORT).show()
        }
    }




}