package com.info.firebaseauth.utils


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

