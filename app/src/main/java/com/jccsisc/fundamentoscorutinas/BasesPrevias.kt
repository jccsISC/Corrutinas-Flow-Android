package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
//    lambda()
//    threads()
//    coroutinesVsThreads()
    sequences()
}

fun sequences() {
    newTopic("Sequences")
    getDatabySeq().forEach { println("${it}°") }
}

fun getDatabySeq(): Sequence<Float> {
    return sequence {
        (1..5).forEach {
            println("Procesando algo...")
            Thread.sleep(someTime())
            yield(20 + it + Random.nextFloat())
        }
    }
}

fun coroutinesVsThreads() {
    newTopic("Corrutinas vs Threads")

    runBlocking {
        (1..1_000_000).forEach { _ ->
            launch {
                delay(someTime())
                println("*")
            }
        }
    }

   /* (1..1_000_000).forEach {
        Thread.sleep(someTime())
        println("*")
    }*/
}

private const val SEPARATOR = "===================="
fun newTopic(topic: String) {
    println("\n$SEPARATOR $topic $SEPARATOR\n")
}

fun threads() {
    newTopic("Threads")
    println("Thread ${multiThread(2, 6)}")
    multiThreadLambda(2,6) {
        println("Thread + Lambda $it")
    }
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0
    thread {
        Thread.sleep(someTime())
        result = x * y
    }
//    Thread.sleep(3_000)
    return  result
}

fun multiThreadLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    var result = 0
    thread {
        Thread.sleep(someTime())
        result = x * y
        callback(result)
    }
}

fun someTime(): Long = Random.nextLong(500, 3_000)

fun lambda() {
    newTopic("Lambda")
    println(multi(2,3))
    multiLambda(2, 5) { result ->
        println(result)
    }
}

fun multiLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    callback(x * y)
}

fun multi(x: Int, y: Int) = x * y
