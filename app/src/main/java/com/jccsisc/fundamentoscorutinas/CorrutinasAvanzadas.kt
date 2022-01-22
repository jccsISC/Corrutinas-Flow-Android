package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
    closeChannel()
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
