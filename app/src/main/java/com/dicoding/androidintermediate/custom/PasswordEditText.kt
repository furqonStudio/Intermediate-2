package com.dicoding.androidintermediate.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import com.dicoding.androidintermediate.R

class CustomPassword: AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        doAfterTextChanged { s ->
            if(!Patterns.EMAIL_ADDRESS.matcher(s!!).matches()) {
                if (s.length < 6) {
                    error = context.getString(R.string.password_error)

                }
            }
        }


//        addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                if (s.length < 6) {
//                    error = context.getString(R.string.password_error)
//
//                }
//            }
//
//            override fun afterTextChanged(s: Editable) {}
//        })
    }
}