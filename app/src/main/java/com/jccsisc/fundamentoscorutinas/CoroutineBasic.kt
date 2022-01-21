package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.*

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 07/01/2022 at 23:56
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
//    globalScope()
//    suspendFun()
    newTopic("Constructores de corrutinas")
//    cRunBlocking()
//    cLaunch()
    cAsnyc()

    readLine() //espera a que ingreses por teclado cualquier cosa
}

fun cAsnyc() {
    runBlocking {
        newTopic("Async")
        val result = async {
            startMsg()
            delay(someTime())
            println("acync...")
            endMsg()
            1
        }
        println("Result: ${result.await()}")
    }
}

fun cLaunch() {
    runBlocking {
        newTopic("Launch")
        launch {
            startMsg()
            delay(someTime())
            println("launch...")
            endMsg()
        }
    }
}

fun cRunBlocking() {
    newTopic("RunBlocking")
    runBlocking {
        startMsg()
        delay(someTime())
        println("runBlocking")
        endMsg()
    }
}

fun suspendFun() {
    newTopic("Suspend")
    Thread.sleep(someTime())
    GlobalScope.launch { delay(someTime()) }
}

fun globalScope() {
    newTopic("Global Scope")
    GlobalScope.launch {
        startMsg()
        delay(someTime())
        println("Mi corrutina")
        endMsg()
    }
}

fun startMsg() {
    println("Comenzando corrutina -${Thread.currentThread().name}--")
}

fun endMsg() {
    println("Corrutina -${Thread.currentThread().name}-- finalizada")
}