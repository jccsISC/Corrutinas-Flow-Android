package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 21/01/2022 at 8:22
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
    coldFlow()
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