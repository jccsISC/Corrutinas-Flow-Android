package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 21/01/2022 at 1:12
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
//    dispatchers()
//    nested()
//    changeWithContext()
    basicFlows()
}

fun basicFlows() {
    newTopic("Flows basicos")
    runBlocking {
        launch { getDatabyFlow().collect { println(it) } }

        launch {
            (1..50).forEach {
                delay(someTime() / 10)
                println("Tarea 2...")
            }
        }
    }
}

fun getDatabyFlow(): Flow<Float> = flow {
    (1..5).forEach {
        println("Procesando algo...")
        delay(someTime())
        emit(20 + it + Random.nextFloat())
    }
}

fun getDatabyFlowStatic(): Flow<Float> = flow {
    (1..5).forEach {
        println("Procesando algo...")
        delay(500)
        emit(20 + it + Random.nextFloat())
    }
}

fun changeWithContext() {
    runBlocking {
        newTopic("withContext")
        startMsg()

        /**cambiar el contexto del padre*/
        withContext(newSingleThreadContext("Cursos Android ANT")) {
            startMsg()
            delay(someTime())
            println("CursosAndroidANT")
            endMsg()
        }

        withContext(Dispatchers.IO) {
            startMsg()
            delay(someTime())
            println("Peticion al servidor")
            endMsg()
        }

        endMsg()
    }
}

fun nested() {
    runBlocking {
        newTopic("Anidar")
        val job = launch {
            startMsg()
            launch {
                startMsg()
                delay(someTime())
                println("Otra tarea")
                endMsg()
            }
            val subJob = launch(Dispatchers.IO) {
                startMsg()

                launch(newSingleThreadContext("Cursos Android ANT")) {
                    startMsg()
                    println("tarea cursos Android ANT")
                    endMsg()
                }

                delay(someTime())
                println("tarea en el servidor")
                endMsg()
            }

            delay(someTime() / 4)
            subJob.cancel()
            println("SubJob cancelado...")

            var sum = 0
            (1..100).forEach {
                sum += it
                delay(someTime() / 100)
            }
            println("Sum = $sum")
            endMsg()
        }

        /*delay(someTime()/2)
        job.cancel()
        println("Job cancelado...")*/
    }
}

fun dispatchers() {
    runBlocking {
        newTopic("Dispatchers")
        launch {
            startMsg()
            println("None")
            endMsg()
        }

        launch(Dispatchers.IO) {
            startMsg()
            println("IO")
            endMsg()
        }

        /**Es el menos comun, cuando no se requiere compartir datos con otras corrutinas */
        launch(Dispatchers.Unconfined) {
            startMsg()
            println("Unconfined")
            endMsg()
        }

        /**Solamente se recomienda usar en el cambio de la interfaz en Android*/
        /*launch(Dispatchers.Main) {
            startMsg()
            println("Unconfined")
            endMsg()
        }*/

        /**Para procesos largos o intensos, imagenes, calculos intensos*/
        launch(Dispatchers.Default) {
            startMsg()
            println("Default")
            endMsg()
        }

        launch(newSingleThreadContext("Cursos Android ANT")) {
            startMsg()
            println("Corrutina personalizada con un Dispatcher")
            endMsg()
        }

        newSingleThreadContext("Cursos AndroidANT").use { myContext ->
            launch(myContext) {
                startMsg()
                println("Corrutina personalizada con un Dispatcher2")
                endMsg()
            }
        }
    }
}

