package lib.android.com.kotlinlib.kotlinforandroid

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import lib.android.com.kotlinlib.MainKotlinActivity
import lib.android.com.kotlinlib.R
import org.jetbrains.anko.*

class LoginKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//        verticalLayout {
//            val textView = textView("我是一个TextView")
//            val name = editText("EditText")
//            button { toast("${name.text}") }
//        }
//        verticalLayout {
//            textView("我是一个经常见到") {
//                textSize = sp(17).toFloat()
//                textColor = Color.parseColor("#000000")
//            }.lparams {
//                margin = dip(10)
//                padding = dip(20)
//            }
//        }
//        UI().setContentView(this@LoginKotlinActivity)
        LoginUi().setContentView(this@LoginKotlinActivity)
    }

    lateinit var et_name: EditText
    lateinit var et_pass: EditText

    inner class LoginUi : AnkoComponent<LoginKotlinActivity> {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun createView(ui: AnkoContext<LoginKotlinActivity>): View {
            return with(ui) {
                verticalLayout {
                    backgroundColor = Color.parseColor("#ffffff")//背景白色
                    gravity = Gravity.CENTER_HORIZONTAL
                    imageView(R.mipmap.ic_launcher).lparams {
                        width = dip(100)
                        height = dip(100)
                        topMargin = dip(64)
                    }
                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.bg_frame_corner
                        imageView(R.mipmap.ic_username).lparams(width = wrapContent, height = wrapContent) {
                            leftMargin = dip(12)
                            rightMargin = dip(15)
                        }
                        et_name = editText {
                            hint = "登录账户"
                            hintTextColor = Color.parseColor("#666666")
                            background = null
                            textSize = 16f
                        }
                    }.lparams {
                        width = dip(320)
                        height = dip(40)
                        topMargin = dip(40)
                    }
                    linearLayout {
                        backgroundResource = R.drawable.bg_frame_corner
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL
                        imageView {
                            image = resources.getDrawable(R.mipmap.ic_password)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(12)
                            rightMargin = dip(15)
                        }
                        et_pass = editText {
                            hint = "登录密码"
                            hintTextColor = Color.parseColor("#666666")
                            textSize = 16f
                            background = null
                            inputType=InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }
                    }.lparams(width = dip(320), height = dip(40)) {
                        topMargin = dip(10)
                    }
                    button {
                        text = "登录"
                        textColor = Color.parseColor("#ffffff")
                        textSize = 16f
                        gravity = Gravity.CENTER
                        width = dip(320)
                        height = dip(40)
                        backgroundResource = R.drawable.bg_login_btn
                        onClick {
                            if (et_name.text.toString().isNotEmpty() && et_pass.text.toString().isNotEmpty())
                                startActivity<MainKotlinActivity>("account" to et_name.text.toString(), "password" to et_pass.text.toString()) else toast("请输入账户或者密码")
                        }
                    }.lparams {
                        topMargin = dip(20)
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL
                        checkBox("记住密码") {
                            textColor = Color.parseColor("#666666")
                            textSize = 16f
                        }
                        textView("隐私协议") {
                            textColor = Color.parseColor("#1783e3")
                            gravity = Gravity.RIGHT
                            textSize = 16f
                            onClick {
                                toast("隐私协议")
                            }
                        }.lparams {
                            weight = 1f
                        }
                    }.lparams(width = dip(320)) {
                        topMargin = dip(24)
                    }
                    linearLayout {
                        gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                        textView("Copyright © Code4Android") {
                            textSize = 14f
                        }
                    }.lparams {
                        bottomMargin = dip(40)
                        weight = 1f
                    }
                }
            }
        }
    }

    inner class UI : AnkoComponent<LoginKotlinActivity> {
        override fun createView(ui: AnkoContext<LoginKotlinActivity>): View {
            return with(ui) {
                verticalLayout {
                    textView("我是一个经常见到") {
                        textSize = sp(17).toFloat()
                        textColor = Color.parseColor("#000000")
                    }.lparams {
                        margin = dip(10)
                        padding = dip(20)
                        width = matchParent
                    }
                    val name = editText("EditText")
                    button {
                        onClick { toast("hello ${name.text}!") }
                    }
                }
            }
        }
    }
}
