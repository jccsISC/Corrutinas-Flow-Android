package com.jccsisc.fundamentoscorutinas

import kotlin.concurrent.thread
import kotlin.random.Random

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 04/01/2022 at 0:27
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
    lambda()
    threads()
}

fun threads() {
    println("Thread ${multiThread(2, 6)}")
    multiThreadLambda(2,6) {
        println("Thread + Lambda $it")
    }
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0
    thread {
        Thread.sleep(somTime())
        result = x * y
    }
//    Thread.sleep(3_000)
    return  result
}

fun multiThreadLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    var result = 0
    thread {
        Thread.sleep(somTime())
        result = x * y
        callback(result)
    }
}

fun somTime(): Long = Random.nextLong(500, 3_000)

fun lambda() {
    println(multi(2,3))

    multiLambda(2, 5) { result ->
        println(result)
    }
}

fun multiLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    callback(x * y)
}

fun multi(x: Int, y: Int): Int {
    return x * y
}
