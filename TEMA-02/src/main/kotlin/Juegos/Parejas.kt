fun main(){
    var size: Int
    do {
        size = responseNum("Introduce el numero de casillas, tiene que ser par: (4-24)", 4, 24)
    }while (size % 2 != 0)

    val tablero = Array(size){ IntArray(size){ 0 } }
    val tableroVisible = Array(size){ Array(size){ "" } }

    // Asignar parejas
    generarParejas(tablero)

    //Desvelar Y Comprobar
    repeat(2){
        val pos = responsePos(tablero, "Introduce la posición donde quieres desvelar:")
        tableroVisible[pos[0]][pos[1]] = tablero[pos[0]][pos[1]].toString()
    }

    mostrarTablero(tablero)
}

fun responsePos(tablero: Array<IntArray>, s: String): IntArray {
    println(s)
    val regexPos = Regex("[1-]+[1]+")
    var response: String
    do {
        response = readln()
        if (!regexPos.matches(response)) println("Formato incorrecto")
    }while (!regexPos.matches(response))
    val responseSplit = response.split('-')
    val pos = IntArray(responseSplit.size)
    for (i in responseSplit.indices){
        pos[i] = responseSplit[i].toInt() -1
    }
    return pos
}

fun generarParejas(tablero: Array<IntArray>) {
    for (i in 1..(tablero.size * tablero.size)/2){
        do {
            val pos = arrayOf(tablero.indices.random(), tablero.indices.random())
            val posPareje = arrayOf(tablero.indices.random(), tablero.indices.random())
            if (tablero[pos[0]][pos[1]] == 0 &&
                tablero[posPareje[0]][posPareje[1]] == 0){

                tablero[pos[0]][pos[1]] = i
                tablero[posPareje[0]][posPareje[1]] = i
                break
            }
        }while (true)
    }
}

private fun mostrarTablero(tablero: Array<IntArray>){
    println("Tablero: \n")
    val matriz = Array(tablero.size){ Array(tablero.size){ "" } }
    for (i in tablero.indices){
        for (j in tablero[i].indices){
            when(tablero[i][j]){
                in 0..9 -> colorCasilla(i, j, matriz, " ${tablero[i][j]} ")
                in 10..99 -> colorCasilla(i, j, matriz, "${tablero[i][j]} ")
                in 100..999 -> colorCasilla(i, j, matriz, "${tablero[i][j]}")
            }
        }
    }

    for (i in matriz){
        for (j in i){
            //print("$j\t\t")
            print(j)
        }
        println()
    }
    println("\n")
}