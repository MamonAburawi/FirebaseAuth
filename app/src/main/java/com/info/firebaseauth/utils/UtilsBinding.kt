package com.info.firebaseauth.utils


import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter



@BindingAdapter("setAuthError")
fun setAuthError(tv: TextView, error: String?){
    if (!error.isNullOrBlank()){
        tv.text = error
        tv.show()
    }else{
        tv.text = ""
        tv.hide()
    }
}



@BindingAdapter("isLoading")
fun isLoading(process: ProgressBar, isLoading: Boolean){
    if (isLoading){
        process.show()
    }else{
        process.hide()
    }
}


@BindingAdapter("switchToEd")
fun switchToEd(ed: EditText, isByPhone: Boolean){
    if (isByPhone){
        ed.hint = "Enter your phone number"
        ed.inputType = InputType.TYPE_CLASS_NUMBER
    }else{
        ed.hint = "Enter your email"
        ed.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }
}

@BindingAdapter("switchToBtn")
fun switchToBtn(btn: Button, isByPhone: Boolean){
    if (isByPhone){
       btn.text = "By Email"
    }else{
        btn.text = "By Phone"
    }
}


