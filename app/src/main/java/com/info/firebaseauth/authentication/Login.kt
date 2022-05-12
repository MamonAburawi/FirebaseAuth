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
import com.info.firebaseauth.databinding.LoginBinding

class Login : Fragment() {

    private lateinit var binding: LoginBinding
    private var e: String = ""
    private var p: String = ""

    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.login, container, false)


        setViews()
        setObserves()


        return binding.root
    }

    private fun setObserves() {

        binding.apply {

            authViewModel = viewModel
            lifecycleOwner = this@Login



            /** login live data **/
            viewModel.isLogin.observe(viewLifecycleOwner) {
                if (it != null){
                    if (it) {
//                       viewModel.loginIsDone()
//                        navigateToHome()
                    }
                }
            }


        }

    }


//    fun navigateToHome() {
//        val intent = Intent(requireContext(),MainActivity::class.java)
//        startActivity(intent)
//    }

    private fun setViews() {

        binding.apply {

            /** login button **/
            btnLogin.setOnClickListener {

                e = email.text.trim().toString()
                p = password.text.trim().toString()

                if (e.isNotBlank() && p.isNotBlank()){
                    viewModel.signIn(e,p)
                }else{
                    Toast.makeText(requireContext(),"fill information", Toast.LENGTH_LONG).show()
                }
            }

            /** sign up button **/
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_login_to_registration)
            }

        }
    }


}