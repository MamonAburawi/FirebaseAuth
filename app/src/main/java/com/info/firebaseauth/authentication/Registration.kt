package com.info.firebaseauth.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.firebaseauth.R
import com.info.firebaseauth.databinding.RegistrationBinding
import com.info.firebaseauth.utils.hide
import com.info.firebaseauth.utils.show


class Registration : Fragment() {
    private lateinit var binding: RegistrationBinding

    private val viewModel by viewModels<AuthViewModel>()

    private var e: String = ""
    private var p: String = ""

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
            viewModel.isRegister.observe(viewLifecycleOwner){ user ->
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
                e = email.text.trim().toString()
                p = password.text.trim().toString()

                if (e.isNotBlank() && p.isNotBlank()){
                    viewModel.signUp(e,p)
                }else{
                    Toast.makeText(requireContext(),"fill information",Toast.LENGTH_LONG).show()
                }

            }

            /** login button **/
            btnSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_registration_to_login)
            }


        }
    }

}