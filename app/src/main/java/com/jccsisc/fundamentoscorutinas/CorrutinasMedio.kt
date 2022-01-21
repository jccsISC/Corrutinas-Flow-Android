package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 21/01/2022 at 1:12
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/
fun main() {
    dispatchers()
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
    }
}

