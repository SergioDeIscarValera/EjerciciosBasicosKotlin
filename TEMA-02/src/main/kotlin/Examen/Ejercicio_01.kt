import kotlin.math.truncate

fun main(){
    var numPrimero = leerNumero()
    var numSegundo = leerNumero()

    val suma = numPrimero + numSegundo

    //Descomposicion del primer número, sin array :(
    //region Descomposición
    var unidadPrimero = 0
    var decenaPrimero = 0
    var centenaPrimero = 0
    var umilesPrimero = 0
    var dmilesPrimero = 0

    for (i in 0..4){
        val newValue = numPrimero % 10
        when(i){
            0 -> unidadPrimero = newValue
            1 -> decenaPrimero = newValue
            2 -> centenaPrimero = newValue
            3 -> umilesPrimero = newValue
            4 -> dmilesPrimero = newValue
        }
        numPrimero = truncate(numPrimero.toFloat() / 10).toInt()
    }
    //endregion

    //Descomposicion del segundo número, sin array :(
    //region Descomposición
    var unidadSegundo = 0
    var decenaSegundo = 0
    var centenaSegundo = 0
    var umilesSegundo = 0
    var dmilesSegundo = 0

    for (i in 0..4){
        val newValue = numSegundo % 10
        when(i){
            0 -> unidadSegundo = newValue
            1 -> decenaSegundo = newValue
            2 -> centenaSegundo = newValue
            3 -> umilesSegundo = newValue
            4 -> dmilesSegundo = newValue
        }
        numSegundo = truncate(numSegundo.toFloat() / 10).toInt()
    }
    //endregion

    println()
    println("Suma: \n")
    println("$dmilesPrimero$umilesPrimero$centenaPrimero$decenaPrimero$unidadPrimero")
    println("$dmilesSegundo$umilesSegundo$centenaSegundo$decenaSegundo$unidadSegundo")
    println("─────")

    Thread.sleep(1_500)

    val decenaAcarreo = if (unidadPrimero + unidadSegundo > 9) { 1 } else { 0 }
    val centenaAcarreo = if (decenaPrimero + decenaSegundo > 9) { 1 } else { 0 }
    val umilesAcarreo = if (centenaPrimero + centenaSegundo > 9) { 1 } else { 0 }
    val dmilesAcarreo = if (umilesPrimero + umilesSegundo > 9) { 1 } else { 0 }
    val cmilesAcarreo = if (dmilesPrimero + dmilesSegundo > 9) { 1 } else { 0 }

    println()
    println("Suma:")
    println("$cmilesAcarreo$dmilesAcarreo$umilesAcarreo$centenaAcarreo$decenaAcarreo"+"_")
    println("_$dmilesPrimero$umilesPrimero$centenaPrimero$decenaPrimero$unidadPrimero")
    println("_$dmilesSegundo$umilesSegundo$centenaSegundo$decenaSegundo$unidadSegundo")
    println("──────")

    Thread.sleep(1_500)

    val sumaString = StringBuilder()
    for (i in 0..(5- contadorCifras(suma))){
        sumaString.append("0")
    }
    sumaString.append(suma)
    println()
    println("Suma:")
    println("_$dmilesPrimero$umilesPrimero$centenaPrimero$decenaPrimero$unidadPrimero")
    println("_$dmilesSegundo$umilesSegundo$centenaSegundo$decenaSegundo$unidadSegundo")
    println("──────")
    println(sumaString.toString())

}

fun leerNumero(): Int {
    println("Introduce un número de máximo 5 cifras:")
    var response: Int
    do {
        response = readln().toIntOrNull() ?: Int.MIN_VALUE
        if(response == Int.MIN_VALUE || response > 99999){ println("Valor invalido") }
    }while (response == Int.MIN_VALUE || response > 99999)
    return response
}

fun contadorCifras(num: Int): Int{
    return when(num){
        in -9..9 -> 1
        in -99..99 -> 2
        in -999..999 -> 3
        in -9999..9999 -> 4
        else -> 5
    }
}