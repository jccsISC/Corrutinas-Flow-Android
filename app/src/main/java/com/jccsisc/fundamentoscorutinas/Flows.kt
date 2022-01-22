package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 21/01/2022 at 8:22
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
//    coldFlow()
//    cancelFlow()
    operadoresFlow()
}

fun operadoresFlow() {
    runBlocking {
        newTopic("Operadores intermediarios")
        newTopic("Map")
        getDatabyFlow()
                .map {
                    setFormat(it)
                    setFormat(convertCelsToFahr(it), "F")
                }
                //.collect { println(it) }

        newTopic("Filter")
        getDatabyFlow()
                .filter {
                     it < 23
                }
                .map {
                    setFormat(it)
                }
                //.collect { println(it) }

        newTopic("Transform")
        getDatabyFlow()
                .transform {
                    emit(setFormat(it))
                    emit(setFormat(convertCelsToFahr(it), "F"))
                }
                .collect { println(it) }
    }
}

fun convertCelsToFahr(cels: Float) = ((cels * 9) / 5) + 32

//                                                  Redondeamos la cantidad a 1 solo decimal
fun setFormat(temp: Float, degree: String = "C") = String.format(Locale.getDefault(), "%.1f $degree", temp)

fun cancelFlow() {
    runBlocking {
        newTopic("Cancelar Flow")
        val job = launch {
            getDatabyFlow().collect { println(it) }
        }

        delay(someTime() * 2)
        job.cancel()
        println("Job cancelado")
    }
}

fun coldFlow() {
    newTopic("Flows are Cold")
    runBlocking {
        val dataFlow = getDatabyFlow()
        println("Esperando...")
        delay(someTime())
        dataFlow.collect { println(it) }
    }
}
