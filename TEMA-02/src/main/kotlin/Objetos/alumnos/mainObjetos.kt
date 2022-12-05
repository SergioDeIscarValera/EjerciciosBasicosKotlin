package Objetos.alumnos

import Objetos.alumnos.models.Vehicle

fun main(){
    /*
        Las clases pasan por referencia como los array, ya que no se sabe de ante mano cuanto va a ocupar.
        Al ser dinámico se almacena en el Heap.

        POJO/POKO (objeto contendor) -> Objeto con contructor, getter & setter, equals, hasCode y toString.
     */
    val vehiculos = Array<Vehicle?>(10){ null }

    for (i in 0..(0..9).random()){
        vehiculos[i] = Vehicle(true)
        println()
    }

    Thread.sleep(1000)
    println()

    for (i in vehiculos){
        if (i != null) println(i.info())
    }

    Thread.sleep(1000)
    println()

    //if ( modelo != "Minicompacto" && modelo != "Subcompacto" && modelo != "Familiar" && modelo != "Monovolúmenes" )
    val countModelos = contarModelos(4, vehiculos)
    val countViejos = contarViejos(vehiculos)
    for (i in countModelos.indices){
        when(i){
            0 -> println("Minicompacto hay un total de ${countModelos[i]}.")
            1 -> println("Subcompacto hay un total de ${countModelos[i]}.")
            2 -> println("Familiar hay un total de ${countModelos[i]}.")
            3 -> println("Monovolúmenes hay un total de ${countModelos[i]}.")
        }
    }

    Thread.sleep(1000)
    println()

    println("Hay un total de $countViejos coches viejos.")

    Thread.sleep(1000)
    println()

    println("La media de los años es ${mediaVehicles(vehiculos)}.")
}

private fun contarModelos(countModels: Int, vehiculos:Array<Vehicle?>): Array<Int> {
    val newArray = Array(countModels){ 0 }
    for (i in vehiculos) {
        if (i != null) {
            when (i.getModelo()) {
                "Minicompacto" -> newArray[0]++
                "Subcompacto" -> newArray[1]++
                "Familiar" -> newArray[2]++
                "Monovolúmenes" -> newArray[3]++
            }
        }
    }
    return newArray
}

private fun contarViejos(vehiculos:Array<Vehicle?>): Int {
    var count = 0
    for (i in vehiculos) {
        if (i != null && i.isOld()) {
            count++
        }
    }
    return count
}

private fun mediaVehicles(vehiculos:Array<Vehicle?>): Int {
    var count = 0
    var indexCount = 0
    for (i in vehiculos) {
        if (i != null) {
            count += i.getDateCreation()
            indexCount++
        }
    }
    return (count/indexCount)
}