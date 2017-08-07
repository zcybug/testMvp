package com.android.mvp.kotlin

/**
 *================================================
 *项目名称：MVP1
 *类 名 称：
 *创 建 人：zhouchunyu
 *描    述：
 *创建时间：2017/7/19 0019  下午 1:22
 * 修改历史：
 *================================================
 */
fun main(args: Array<String>) {
//        print(getStringLenght(11))
//    fun max(a: Int, b: Int) = if (a > b) a else b
//    print(max(10, 20))

//    val a: Int = 10000
//    print (a === a ) //打印 'true'
//    val boxedA: Int? =a
//    val anotherBoxedA: Int? = a
//    print (boxedA === anotherBoxedA ) //注意这里打印的是 'false'

//    val a: Double = 100.5
//    val b: Int = a.toInt()
//    print("b==" + b)

//    val a = 1.toLong() + 1
//    print("a=" + a)

    val x: IntArray = intArrayOf(1, 2, 3)
    x[0] = x[1] + x[2]
    for (i in x.indices)
        println("x=" + x[i])
//    testWhen(30)
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum(a: Double, b: Int): Unit {
    print("a+b=" + (a + b))
}

fun getStringLenght(obj: Any): Int? {
    if (obj is String)
    //obj 将会在这个分支中自动转换为 String 类型
        return obj.length
    // obj 在种类检查外仍然是 Any 类型
    return null
}

fun max(a: Int, b: Int): Int {
    if (a > b)
        return a
    else
        return b
}


fun testWhen(a: Int) {
    when (a) {
        in 1..10 -> print("a is 1-10")
        !in 10..20 -> print("a is not 10-20")
        else -> { //Note the block
            print("x is neither 1 nor 2")
        }
    }
}