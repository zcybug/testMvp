package com.android.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView

/**
 * Created by Administrator on 2017/6/27 0027.
 */

class MainAc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_ac_layout)
    }

    fun sayHello(v: View) {
        val textView = findViewById(R.id.textView) as TextView
        val editText = findViewById(R.id.editText) as EditText
        textView.text = "Hello, " + editText.text.toString() + "!"
    }
}
