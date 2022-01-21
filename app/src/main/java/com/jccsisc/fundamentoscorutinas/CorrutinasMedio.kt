package com.jccsisc.fundamentoscorutinas

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
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

        newSingleThreadContext("Cursos AndroidANT").use {myContext->
            launch(myContext) {
                startMsg()
                println("Corrutina personalizada con un Dispatcher2")
                endMsg()
            }
        }
    }
}

