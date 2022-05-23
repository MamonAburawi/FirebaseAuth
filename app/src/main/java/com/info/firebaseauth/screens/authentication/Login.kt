package com.info.firebaseauth.screens.authentication


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.info.firebaseauth.R
import com.info.firebaseauth.databinding.LoginBinding
import com.info.firebaseauth.databinding.ResetPassBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Login : Fragment() {

    companion object{
        const val TAG = "Login"
    }

    private lateinit var binding: LoginBinding
    private var _email: String = ""
    private var _phone: String = ""
    private var _password: String = ""

    private val viewModel by sharedViewModel<AuthViewModel>()

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
                        Toast.makeText(requireContext(),"Login Success",Toast.LENGTH_SHORT).show()
                    }
                }
            }

            /** reset password error **/
            viewModel.resetPassError.observe(viewLifecycleOwner){
                if (!it.isNullOrBlank()){
                    Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
                }
            }


            /** is reset password success **/
            viewModel.isResetPassSuccess.observe(viewLifecycleOwner){ isSuccess ->
                if (isSuccess != null && isSuccess){
                    Toast.makeText(requireContext(),"Reset request has been send",Toast.LENGTH_SHORT).show()
                }
            }


        }

    }




    private fun setViews() {

        binding.apply {

            /** login button **/
            btnLogin.setOnClickListener {
                signIn()
            }

            /** sign up button **/
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_login_to_registration)
            }


            /** reset password button **/
            btnResetPassword.setOnClickListener {
                resetPassDialog()
            }

            /** button switch **/
            btnSwitch.setOnClickListener {
                viewModel.switchTo()
            }


        }
    }



    private fun resetPassDialog() {
        var alertDialog: AlertDialog? = null
        val binding = DataBindingUtil.inflate<ResetPassBinding>(layoutInflater,R.layout.reset_pass,null,false)

        val builder = AlertDialog.Builder(requireContext())
        alertDialog = builder.setView(binding.root).show()

        binding.apply {
            /** button reset password **/
            btnResetPassword.setOnClickListener {
                val e = email.text.trim().toString()
                if (e.isNotBlank()){
                    viewModel.resetPassword(e)
                    alertDialog.dismiss()
                }else{
                    email.error = "email is required!"
                    email.requestFocus()
                }
            }
        }
    }


    private fun signIn(){
        binding.apply {
            _email = email.text.trim().toString()
            _password = password.text.trim().toString()

            if (viewModel.isByPhone.value!!){ // sign by phone
                if(_phone.isNotEmpty()){

                }


            }else{ // sign by email
                if(_email.isNotEmpty()){
                    viewModel.signInWithEmail(_email,_password)
                }

            }



        }
    }




}





