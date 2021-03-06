package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

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
//    cAsnyc()
//    job()
//    deferred()
    cProduce()

    readLine() //espera a que ingreses por teclado cualquier cosa
}

fun cProduce() {
    runBlocking {
        newTopic("Produce")
        val names = produceNames()
        names.consumeEach { println(it) }
    }
}

/**Funcion de extension*/
fun CoroutineScope.produceNames(): ReceiveChannel<String> = produce {
    (1..5).forEach { send("name $it") }
}

fun deferred() {
    runBlocking {
        newTopic("Deferred")
        val deferred = async {
            startMsg()
            delay(someTime())
            println("deferred...")
            endMsg()
            multi(5, 2)
        }

        println("Deferred: $deferred")
        println("Valor del deferred.await: ${deferred.await()}")

        val result = async {
            multi(3,3)
        }.await()

        println("Result: $result")
    }
}

fun job() {
    runBlocking {
        newTopic("Job")
        val job = launch {
            startMsg()
            delay(4_000)
            println("job...")
            endMsg()
        }
        println("Job: $job")

//        delay(5_000)
        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")

        delay(someTime())
        println("Tarea cancelada o interrumpida")
        job.cancel()
        println("isActive: ${job.isActive}")
        println("isCancelled: ${job.isCancelled}")
        println("isCompleted: ${job.isCompleted}")
    }
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