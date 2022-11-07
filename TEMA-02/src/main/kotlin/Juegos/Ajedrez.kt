const val ANSI_WHITE_BACKGROUND = "\u001B[47m"
const val ANSI_BLACK_BACKGROUND = "\u001B[40m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_WHITE = "\u001B[37m"
const val ANSI_RESET = "\u001B[0m"
fun main(){
    val tablero = Array(8){ IntArray(8) }
    mostrarTablero(tablero)

    val responsePieza = introducirPieza()
    val responsePosition = intoducirPosicion(tablero, responsePieza)

    //Calcular posibles movimientos
    when(responsePieza){
        2 -> movimientoTorre(tablero, responsePosition) // torre
        3 -> movimientoAlfil(tablero, responsePosition) // alfil
        4 -> {movimientoTorre(tablero, responsePosition); movimientoAlfil(tablero, responsePosition)} // dama
        5 -> movimientoCaballo(tablero, responsePosition) // caballo
    }

    mostrarTablero(tablero)
}

fun movimientoCaballo(tablero: Array<IntArray>, position: Array<Int>) {
    // Izquierda Arriba
    branchMovementCaballo(-1, -1, arrayOf(position[0] - 1 ,position[1] - 1), tablero)
    // Izquierda Abajo
    branchMovementCaballo(-1, 1, arrayOf(position[0] + 1,position[1] - 1), tablero)

    // Derecha Arriba
    branchMovementCaballo(1, -1, arrayOf(position[0] - 1,position[1] + 1), tablero)
    // Derecha Abajo
    branchMovementCaballo(1, 1, arrayOf(position[0] + 1,position[1] + 1), tablero)
}

fun branchMovementCaballo(row: Int, column: Int, position: Array<Int>, tablero: Array<IntArray>){
    //Comporbar column
    if(position[0] + column in tablero.indices && position[1] in tablero.indices){
        tablero[position[0] + column][position[1]] = 1
    }

    //Comprobar row
    if(position[1] + row in tablero.indices && position[0] in tablero.indices){
        tablero[position[0]][position[1] + row] = 1
    }
}

fun movimientoAlfil(tablero: Array<IntArray>, position: Array<Int>) {
    // Izquierda Arriba
    recursiveMovementAlfil(-1, -1, position, tablero)
    // Izquierda Abajo
    recursiveMovementAlfil(1, -1, position, tablero)

    // Derecha Arriba
    recursiveMovementAlfil(-1, 1, position, tablero)
    // Derecha Abajo
    recursiveMovementAlfil(1, 1, position, tablero)
}

fun recursiveMovementAlfil(row: Short, column: Short, position: Array<Int>, tablero: Array<IntArray>){
    if((position[0] + row) in tablero.indices && (position[1] + column) in tablero.indices){
        tablero[position[0] + row][position[1] + column] = 1
        val newPos = arrayOf((position[0] + row), (position[1] + column))
        recursiveMovementAlfil(row, column, newPos, tablero)
    }
}

fun movimientoTorre(tablero: Array<IntArray>, position: Array<Int>) {
    /*for (i in tablero.indices){
        if (i == position[0]){
            for (j in tablero[i].indices){
                if (j != position[1]){
                    tablero[i][j] = 1
                }
            }
        }else{
            for (j in tablero[i].indices){
                if (j == position[1]){
                    tablero[i][j] = 1
                }
            }
        }
    }*/

    for (i in tablero.indices){
        for (j in tablero[i].indices){
            if ((j != position[1] && i == position[0]) || (j == position[1] && i != position[0])){
                tablero[i][j] = 1
            }
        }
    }
}

fun intoducirPosicion(tablero: Array<IntArray>, responsePieza: Int): Array<Int> {
    println("Introduce la posición donde vas a colocar la pieza:")
    println("Primero la columna:")
    val column = responseNum() - 1 // Se resta para ajustar al array
    println("Ahora la fila:")
    val row = responseNum() - 1

    tablero[row][column] = responsePieza
    return intArrayOf(row, column).toTypedArray()
}

private fun responseNum(): Int{
    var response: Int
    do {
        response = readln().toIntOrNull() ?: -1
    }while (response !in 1..8) // minimos y maximos del tablero
    return response
}

fun introducirPieza(): Int {
    println("Introduce la pieza que vas a poner (♖, ♗, ♕, ♘):")
    var response: String
    do {
        response = readln()
        if (response == "♖" || response == "♗" || response == "♕" ||response == "♘"){ break }
        else { println("Introduce un valor valido (♖, ♗, ♕, ♘)") }
    }while (true)
    return when(response){
        "♖" -> 2 // torre
        "♗" -> 3 // alfil
        "♕" -> 4 // dama
        "♘" -> 5 // caballo
        else -> {-1}
    }
}

private fun mostrarTablero(tablero: Array<IntArray>) {
    println("Tablero: \n")
    val matriz = Array(8){ Array(8){""} }
    for (i in tablero.indices){
        for (j in tablero[i].indices){
            when(tablero[i][j]){
                0 ->{ colorCasilla(i, j, matriz, " ") } // nada
                1 ->{ colorCasilla(i, j, matriz, "◉") } // posible movimiento
                2 ->{ colorCasilla(i, j, matriz, "♖") } // torre
                3 ->{ colorCasilla(i, j, matriz, "♗") } // alfil
                4 ->{ colorCasilla(i, j, matriz, "♕") } // dama
                5 ->{ colorCasilla(i, j, matriz, "♘") } // caballo
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

fun colorCasilla(row: Int, column: Int, matriz:Array<Array<String>>, text: String){
    if (row % 2 == 0){
        if (column % 2 == 0){ // Blanco
            matriz[row][column] = "$ANSI_WHITE_BACKGROUND$ANSI_BLACK $text $ANSI_RESET"
        }else{ // Negro
            matriz[row][column] = "$ANSI_BLACK_BACKGROUND$ANSI_WHITE $text $ANSI_RESET"
        }
    }else{
        if (column % 2 == 0){ // Negro
            matriz[row][column] = "$ANSI_BLACK_BACKGROUND$ANSI_WHITE $text $ANSI_RESET"
        }else{ // Blanco
            matriz[row][column] = "$ANSI_WHITE_BACKGROUND$ANSI_BLACK $text $ANSI_RESET"
        }
    }
}