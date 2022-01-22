package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.channels.Channel
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
    basicChannel()
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
