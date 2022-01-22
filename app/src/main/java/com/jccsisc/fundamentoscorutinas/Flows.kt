package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

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
//    operadoresFlow()
//    terminalFlowOperators()
//    bufferFlow()
//    conflateFlow()
    multiFlow()
}

fun multiFlow() {
    runBlocking {
        newTopic("Zip y Combine")
        //mezclar 2 flujos zip
        getDatabyFlowStatic()
                .map { setFormat(it) }
                .combine(getMatchResultFlow()) { degrees, result->
//                .zip(getMatchResultFlow()) { degrees, result->
                    "$result with $degrees"
                }
                .collectLatest { println(it) }
    }
}

fun conflateFlow() {
    runBlocking {
        newTopic("Funsión")
        val time = measureTimeMillis {
            getMatchResultFlow()
                    .conflate()
                    .collectLatest {
                    //.collect {
                        delay(100)
                        println(it)
                    }
        }
        println("Time: ${time} ms")
    }
}

fun getMatchResultFlow() = flow {
    var homeTeam = 0
    var awayTeam = 0
    (0..45).forEach {
        println("Minuto: $it")
        delay(50)
        homeTeam += Random.nextInt(0, 21) / 20
        awayTeam += Random.nextInt(0, 21) / 20
        emit("$homeTeam-$awayTeam")
    }
}

fun bufferFlow() {
    runBlocking {
        newTopic("Buffer para Flow")
        val time = measureTimeMillis {
            getDatabyFlowStatic()
                    .map { setFormat(it) }
                    .buffer()
                    .collect {
                        delay(500)
                        println(it)
                    }
        }
        println("Time: ${time} ms")
    }

}

fun terminalFlowOperators() {
    runBlocking {
        newTopic("Operadores Flow Terminales")
        newTopic("List Operator")
        val list = getDatabyFlow()
                //.toList()
        println("List: $list")

        newTopic("Single")
        val single = getDatabyFlow()
                //.take(1)
                //.single()
        println("Single: $single")

        newTopic("Fisrt")
        val first = getDatabyFlow()
                //.first()
        println("First: $first")

        newTopic("Last")
        val last = getDatabyFlow()
                //.last()
        println("Last: $last")

        newTopic("Reduce")
        val saving = getDatabyFlow()
                .reduce { accumulator, value ->
                    //acumula tod0o el recorrido o algo en especifico
                    println("Accumulator: $accumulator")
                    println("Value: $value")
                    println("Current saving: ${accumulator + value}")
                    accumulator + value
                }
        println("Saving: $saving")

        newTopic("Fold")
        val lastSaving = saving
        val totalSaving = getDatabyFlow()
                .fold(lastSaving, { acc, value->
                    println("Accumulator: $acc")
                    println("Value: $value")
                    println("Current saving: ${acc + value}")
                    acc + value
                })
        println("Total saving: $totalSaving")

    }
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
                //.collect { println(it) }

        newTopic("Take")
        getDatabyFlow()
                .take(3)
                .map { setFormat(it) }
                //.collect { println(it) }


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
