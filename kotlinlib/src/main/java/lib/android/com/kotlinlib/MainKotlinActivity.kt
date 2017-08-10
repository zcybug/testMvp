package lib.android.com.kotlinlib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)
        val name="我是一个TextV"
    }
}
