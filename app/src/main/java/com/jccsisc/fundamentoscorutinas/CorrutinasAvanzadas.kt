package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.lang.Exception
import java.util.concurrent.TimeoutException

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 22/01/2022 at 7:31
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/

val countries = listOf("Santander", "CDMX", "Lima", "Buenos Aires", "Apatzingán", "Puerto Vallarta")

fun main() {
//    basicChannel()
//    closeChannel()
//    produceChannel()
//    pipelines()
//    bufferChannel()
    exceptionHandler()
    readLine()
}

fun exceptionHandler() {
    //nos regresa el contexto de la corrutina y la excepcion
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Notifica al programado... $throwable in $coroutineContext")
    }

    runBlocking {
        newTopic("Manejo de excepciones")
        launch {
            try {
                delay(100)
//                throw Exception()
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val globalScope = CoroutineScope(Job() + exceptionHandler) //contiene una corrutina personalizada
        globalScope.launch {
            delay(200)
            throw TimeoutException()
        }
    }
}

fun bufferChannel() {
    runBlocking {
        newTopic("Buffer para channels")
        val time = System.currentTimeMillis() //para medir el tiempo de ejecución
        val channel = Channel<String>()
        launch {
            countries.forEach {
                delay(100)
                channel.send(it)
            }
            channel.close()
        }
        launch {
            delay(1_000)
            channel.consumeEach { println(it) }
            println("Time: ${System.currentTimeMillis() - time } ms")
        }

        val bufferTime = System.currentTimeMillis() //para medir el tiempo de ejecución
        val bufferChannel = Channel<String>(3)
        launch {
            countries.forEach {
                delay(100)
                bufferChannel.send(it)
            }
            bufferChannel.close()
        }
        launch {
            delay(1_000)
            bufferChannel.consumeEach { println(it) }
            println("BTime: ${System.currentTimeMillis() - bufferTime } ms")
        }
    }
}

fun pipelines() {
    runBlocking {
        newTopic("Pipelines")
        val citiesChannels = produceCities()
        val foodChannels = produceFoods(citiesChannels)
        foodChannels.consumeEach { println(it) }
        citiesChannels.cancel()
        foodChannels.cancel()
        println("Todo está 10/10")
    }
}
//canal que consumirá el resultado de las cities
fun CoroutineScope.produceFoods(cities: ReceiveChannel<String>): ReceiveChannel<String> = produce {
    for (city in cities) {
        val food = getFoodByCity(city)
        send("$food desde $city")
    }
}
suspend fun getFoodByCity(city: String): String {
    delay(300)
    return when(city) {
        "Santander"-> "Arepa"
        "CDMX" -> "Taco"
        "Lima" -> "Ceviche"
        "Buenos Aires" -> "Milanesa"
        "Apatzingán" -> "Morisqueta"
        "Puerto Vallarta" -> "Arriero"
        else -> "Sin datos"
    }
}

fun produceChannel() {
    runBlocking {
        newTopic("Canales y el patrón productor.consumidor")
        val names = produceCities()
        names.consumeEach { println(it) }
    }
}

fun CoroutineScope.produceCities(): ReceiveChannel<String> = produce {
    countries.forEach {
        send(it)
    }
}

fun closeChannel() {
    runBlocking {
        newTopic("Cerrar un Canal")
        val channel = Channel<String>()
        launch {
            countries.forEach {
                channel.send(it)
                //if (it == "Lima") channel.close()
                if (it == "Lima") {
                    channel.close()
                    return@launch
                }
            }
            //channel.close()
        }
        //consumimos el channel
        /*for (value in channel) {
            println(value)
        }*/

        //esta es una mejor forma de consumir el canal que que no dependeriamos de un repeat con su tamaño específico
        while (!channel.isClosedForReceive) {
            //si el canal no está cerrado, consume
            println(channel.receive())
        }

//        channel.consumeEach { println(it) } //otra forma de consumir el canal
    }
}

fun basicChannel() {
    runBlocking {
        newTopic("Canal básico")
        val channel = Channel<String>()
        launch {
            countries.forEach {
                channel.send(it)
            }
        }
        /*repeat(6) {
            println(channel.receive())
        }*/

        for (value in channel) {
//            println(channel.receive()) //no lo podemos tratar como un arreglo
            println(value)
        }
    }
}
