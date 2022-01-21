package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 07/01/2022 at 23:56
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
//    globalScope()


    runBlocking {
        suspendFun()
    }
    readLine() //espera a que ingreses por teclado cualquier cosa
}

suspend fun suspendFun() {
    newTopic("Suspend")
    Thread.sleep(someTime())
//    GlobalScope.launch { delay(someTime()) }
    delay(someTime())
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