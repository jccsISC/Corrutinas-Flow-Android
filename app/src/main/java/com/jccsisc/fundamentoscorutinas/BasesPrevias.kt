package com.jccsisc.fundamentoscorutinas

/****
 * Project: FundamentosCorutinas
 * From: com.jccsisc.fundamentoscorutinas
 * Created by Julio Cesar Camacho Silva on 04/01/2022 at 0:27
 * More info: https://www.facebook.com/juliocesar.camachosilva/
 * All rights reserved 2022.
 ***/

fun main() {
    lambda()
}

fun lambda() {
    println(multi(2,3))

    multiLambda(2, 5) { result ->
        println(result)
    }
}

fun multiLambda(x: Int, y: Int, callback: (result: Int) -> Unit) {
    callback(x * y)
}

fun multi(x: Int, y: Int): Int {
    return x * y
}
