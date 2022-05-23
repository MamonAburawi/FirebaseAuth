package com.info.firebaseauth.utils


import android.text.InputType
import android.view.View
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


@BindingAdapter("switchToED")
fun switchToED(ed: EditText, isByPhone: Boolean){
    if (isByPhone){
        ed.hide()
    }else{
        ed.show()
    }
}

@BindingAdapter("switchToPhoneLayout")
fun switchToPhoneLayout(view: View, isByPhone: Boolean){
    if (isByPhone){
        view.show()
    }else{
        view.hide()
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


