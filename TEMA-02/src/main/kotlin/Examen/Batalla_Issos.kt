// 1h 15min 45sec

fun main() {
    val HOMBRE_DARIO = 18000
    val HOMBRE_ALEJANDRO = 12000

    // Colocar hombre DARIO en los flancos
    val FLANCOS_DARIO = colocarHombre(HOMBRE_DARIO)

    println("Distribución Darío:")
    println("-------------------")
    printFlancos(FLANCOS_DARIO)
    println()

    // Colocar hombre Alejandro en los flancos
    println("Distribución Alejandro:")
    println("-------------------")
    val FLANCOS_ALEJANDRO = colocarHombreManual(HOMBRE_ALEJANDRO)
    println()

    //Lucha
    val resultadoLucha = flucha(FLANCOS_DARIO, FLANCOS_ALEJANDRO)
    println("Luchando:")
    println("-------------------")
    mostrarResultado(resultadoLucha)
    println()

    // Estado Final
    println("Alejandro: ${FLANCOS_ALEJANDRO.joinToString()}")
    println("Darío: ${FLANCOS_DARIO.joinToString()}")
}

private fun mostrarResultado(resultadoLucha: BooleanArray) {
    var count = 0
    for (i in resultadoLucha.indices) {
        val flancoName = when (i) {
            0 -> "izdo"
            1 -> "centro"
            2 -> "dcho"
            else -> throw IllegalArgumentException("Error")
        }
        if (resultadoLucha[i]){ println("Se ha ganado $flancoName"); count++}
        else println("Se ha perdido $flancoName")
    }
    if (count >= 2){
        println("VICTORIA DE ALEJANDRO!!!")
    }else{
        println("VICTORIA DE DARÍO!!!")
    }
}

// region Lucha
fun flucha(flancosDario: IntArray, flancosAlejandro: IntArray): BooleanArray {
    val resultados = BooleanArray(flancosAlejandro.size)
    for (i in flancosDario.indices){
        resultados[i] = luchaFlanco(flancosAlejandro, i, flancosDario)
    }
    return resultados
}

private fun luchaFlanco(flancosAlejandro: IntArray, i: Int, flancosDario: IntArray): Boolean {
    if (flancosAlejandro[i] >= flancosDario[i]) { // Alejandro - 30% && Dario - 60% -> 70% de victorio
        flancosAlejandro[i] -= flancosAlejandro[i] * 30 / 100
        flancosDario[i] -= flancosDario[i] * 60 / 100
        return (0..100).random() in 0..70
    } else { // Alejandro - 60% && Dariío - 50% -> 50%
        flancosAlejandro[i] -= flancosAlejandro[i] * 60 / 100
        flancosDario[i] -= flancosDario[i] * 50 / 100
        return (0..100).random() in 0..50
    }
}
// endregion

// region Output
private fun printFlancos(flancos: IntArray) {
    for (i in flancos.indices) {
        val flancoName = when (i) {
            0 -> "izdo"
            1 -> "centro"
            2 -> "dcho"
            else -> throw IllegalArgumentException("Error")
        }
        println("Flanco $flancoName: ${flancos[i]}")
    }
}

fun colocarHombre(hombres: Int): IntArray {
    val flancos = IntArray(3) // 0-izq, 1-cen, 2-der
    var hombres = hombres
    val banderas = generateBanderas()
    for (i in banderas){
        var temHombres = 0
        when(i[0]){
            'a' -> temHombres = hombres / 3
            'r' -> temHombres = hombres / 2
            'v' -> temHombres = hombres
        }
        when(i[1]){
            'a' -> flancos[0] = temHombres
            'r' -> flancos[2] = temHombres
            'v' -> flancos[1] = temHombres
        }
        hombres -= temHombres
    }
    return flancos
}

fun generateBanderas(): Array<CharArray> {
    val array = Array(3){ CharArray(2) }

    for (i in array.indices){
        array[i][0] = randomARV()
        when(i){
            0 -> array[i][1] = 'a'
            1 -> array[i][1] = 'r'
            2 -> array[i][1] = 'v'
        }
        println("Banderas: ${array[i][0]}${array[i][1]}")
    }

    return array
}

fun randomARV(): Char {
    return when((0..2).random()){
        0 -> 'a'
        1 -> 'r'
        2 -> 'v'
        else -> throw IllegalArgumentException("Error")
    }
}
// endregion

// region Input
private fun responseNum(message: String, max: Int): Int{
    print(message)
    var response: Int
    do {
        response = readln().toIntOrNull() ?: -1
        if (response < 0 || response > max){ print("Introduce un número valido (Max = $max): ")} else{ break }
    }while (true) // minimos y maximos del tablero
    return response
}

fun colocarHombreManual(hombres: Int): IntArray {
    val flancos = IntArray(3) // 0-izq, 1-cen, 2-der
    var hombres = hombres
    for (i in flancos.indices){
        val flancoName = when(i){
            0 -> "izdo"
            1 -> "centro"
            2 -> "dcho"
            else -> throw IllegalArgumentException("Error")
        }
        val temHombre = responseNum("Flanco $flancoName: ", hombres)
        flancos[i] = temHombre
        hombres -= temHombre
    }
    return flancos
}
// endregion