fun main(){
    val size = responseNum("Introduce el tamaño del tablero que quieres:", 100)
    val tablero = Array(size){ IntArray(size) }

    val countMinas = responseNum("¿Cuantas minas quieres?", (size * size) / 2)
    insertarMinas(tablero, countMinas)

    val posMarkedMines = Array(countMinas){ IntArray(2){ -1 } } // Almacenar las posiciones donde ha marcado las minas

    do {
        mostrarTablero(tablero, posMarkedMines)
    }while (
        desvelarPosicion(tablero, posMarkedMines, responseYesOrNot("Si quieres desvelar introduce S y si quieres marcar introduce N (minas restantes: ${countMinas - countMark(posMarkedMines)})"))
        &&
        countMark(posMarkedMines) != countMinas
    )

    mostrarTablero(tablero, posMarkedMines)

    if (countMark(posMarkedMines) == countMinas){
        if (checkMarks(tablero, posMarkedMines)){
            println("HAS GANADO!!!")
        }else{
            println("F no has marcado bien")
        }
    }else{
        println("BOOOM!!!")
    }
}

// region Marcar
fun thisPosIsMarked(pos: Array<Int>, markedMines: Array<IntArray>, unMark:Boolean): Boolean{
    for (i in markedMines.indices){
        if (markedMines[i][0] == pos[0] && markedMines[i][1] == pos[1] ){
            if (unMark){ markedMines[i] = IntArray(2){ -1 } }
            return true
        }
    }
    return false
}

fun marcarBomba(pos: Array<Int>, markedMines: Array<IntArray>){

    if (thisPosIsMarked(pos, markedMines, true)){
        return
    }

    for (i in markedMines.indices){
        if (markedMines[i][0] == -1 && markedMines[i][1] == -1 ){
            markedMines[i] = pos.toIntArray()
            break
        }
    }
}

fun countMark(markedMines: Array<IntArray>): Int{
    var count = 0
    for (i in markedMines.indices){
        if (markedMines[i][0] != -1 && markedMines[i][1] != -1 ){
            count++
        }
    }
    return count
}

fun checkMarks(tablero: Array<IntArray>, markedMines: Array<IntArray>): Boolean{
    var count = 0
    for (i in markedMines){
        if (tablero[i[0]][i[1]] != -1){
            return false
        }else{
            count++
        }
    }

    if (markedMines.size != count){ return false }

    return true
}
// endregion

// region Desvelar
fun desvelarPosicion(tablero: Array<IntArray>, markedMines: Array<IntArray>, revealOrMark: Boolean): Boolean {
    do {
        if (revealOrMark){
            println("Introduce la posición donde quieres desvelar:")
        }else{
            println("Introduce la posición donde quieres marcar:")
        }

        val column = responseNum("Introduce la columna:", tablero.size) -1
        val row = responseNum("Introduce la fila:", tablero.size) -1

        when(tablero[row][column]){
            -1  -> { if (revealOrMark){ return false }
                    else { marcarBomba(arrayOf(row,column), markedMines); return true } } // golpea mina
            0   -> { if(revealOrMark){desvelado(arrayOf(row, column), tablero); return true}
                    else { marcarBomba(arrayOf(row,column), markedMines); return true } } // mostrar / marcar
            1   -> { println("\nEsta posición ya esta descubierta\n") } // ya mostrado
            else -> return false
        }
    }while (true)
}

fun desvelado(pos: Array<Int>, tablero: Array<IntArray>) {
    if (minasAround(pos,tablero) == 0){
        tablero[pos[0]][pos[1]] = 1
        // region Arriba
        if (pos[0] - 1 in tablero.indices && pos[1] in tablero.indices && tablero[pos[0] -1][pos[1]] == 0){
            desvelado(arrayOf(pos[0] - 1, pos[1]), tablero)
        } // arriba centro

        if (pos[0] - 1 in tablero.indices && pos[1] + 1 in tablero.indices && tablero[pos[0] -1][pos[1] + 1] == 0){
            desvelado(arrayOf(pos[0] - 1, pos[1] + 1), tablero)
        } // arriba derecha

        if (pos[0] - 1 in tablero.indices && pos[1] - 1 in tablero.indices && tablero[pos[0] -1][pos[1] -1] == 0){
            desvelado(arrayOf(pos[0] - 1, pos[1] - 1), tablero)
        } // arriba izquierda
        // endregion

        // region Abajo
        if (pos[0] + 1 in tablero.indices && pos[1] in tablero.indices && tablero[pos[0] + 1][pos[1]] == 0){
            desvelado(arrayOf(pos[0] + 1, pos[1]), tablero)
        } // abajo centro

        if (pos[0] + 1 in tablero.indices && pos[1] + 1 in tablero.indices && tablero[pos[0] + 1][pos[1] + 1] == 0){
            desvelado(arrayOf(pos[0] + 1, pos[1] + 1), tablero)
        } // abajo derecha

        if (pos[0] + 1 in tablero.indices && pos[1] - 1 in tablero.indices && tablero[pos[0] + 1][pos[1] - 1] == 0){
            desvelado(arrayOf(pos[0] + 1, pos[1] - 1), tablero)
        } // abajo izquierda
        // endregion

        // region Lados
        if (pos[0] in tablero.indices && pos[1] + 1 in tablero.indices && tablero[pos[0]][pos[1] + 1] == 0){
            desvelado(arrayOf(pos[0], pos[1] + 1), tablero)
        } // abajo derecha

        if (pos[0] in tablero.indices && pos[1] - 1 in tablero.indices && tablero[pos[0]][pos[1] - 1] == 0) {
            desvelado(arrayOf(pos[0], pos[1] - 1), tablero)
        }
        // endregion
    }else{
        tablero[pos[0]][pos[1]] = 2
    }
}

fun minasAround(pos: Array<Int>, tablero: Array<IntArray>): Int{
    var countMinas = 0

    // region Centro
    if (pos[0] - 1 in tablero.indices && pos[1] in tablero.indices && tablero[pos[0] -1][pos[1]] == -1){
        countMinas++
    } // arriba centro

    if (pos[0] - 1 in tablero.indices && pos[1] + 1 in tablero.indices && tablero[pos[0] -1][pos[1] + 1] == -1){
        countMinas++
    } // arriba derecha

    if (pos[0] - 1 in tablero.indices && pos[1] - 1 in tablero.indices && tablero[pos[0] -1][pos[1] -1] == -1){
        countMinas++
    } // arriba izquierda
    // endregion

    // region Abajo
    if (pos[0] + 1 in tablero.indices && pos[1] in tablero.indices && tablero[pos[0] + 1][pos[1]] == -1){
        countMinas++
    } // abajo centro

    if (pos[0] + 1 in tablero.indices && pos[1] + 1 in tablero.indices && tablero[pos[0] + 1][pos[1] + 1] == -1){
        countMinas++
    } // abajo derecha

    if (pos[0] + 1 in tablero.indices && pos[1] - 1 in tablero.indices && tablero[pos[0] + 1][pos[1] - 1] == -1){
        countMinas++
    } // abajo izquierda
    // endregion

    // region Lados
    if (pos[0] in tablero.indices && pos[1] + 1 in tablero.indices && tablero[pos[0]][pos[1] + 1] == -1){
        countMinas++
    } // abajo derecha

    if (pos[0] in tablero.indices && pos[1] - 1 in tablero.indices && tablero[pos[0]][pos[1] - 1] == -1) {
        countMinas++
    }
    // endregion

    return countMinas
}
// endregion

private fun mostrarTablero(tablero: Array<IntArray>, markedMines: Array<IntArray>){
    println("Tablero: \n")
    val matriz = Array(tablero.size){ Array(tablero.size){""} }
    for (i in tablero.indices){ // fila
        for (j in tablero[i].indices){ // casilla
            if (thisPosIsMarked(arrayOf(i,j), markedMines, false)){
                colorCasilla(i, j, matriz, "M")
            }else{
                when(tablero[i][j]){
                    /*-1 -> { colorCasilla(i, j, matriz, "*") }*/
                    in -1..0 -> { colorCasilla(i, j, matriz, "#") } // No descubierto
                    1 -> { colorCasilla(i, j, matriz, " ") } // nada
                    2 -> { colorCasilla(i, j, matriz, "${minasAround(arrayOf(i, j), tablero)}") } // Minas cerca
                    //  3 -> { colorCasilla(i, j, matriz, "\uD83D\uDEA9") } // Mina marcada
                }
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

fun insertarMinas(tablero: Array<IntArray>, count: Int) {
    val arrayPos = Array(count){ arrayOf(-1,-1) }
    for (i in 0 until count){
        var newPos: Array<Int>
        do {
            var isRepeated = false
            newPos = arrayOf((tablero.indices).random(), (tablero.indices).random())
            loop@for (j in arrayPos){
                if (newPos[0] == j[0] && newPos[1] == j[1]) { isRepeated = true; break@loop }
            }
        }while (isRepeated)
        tablero[newPos[0]][newPos[1]] = -1
        arrayPos[i] = newPos
    }

}

// region Inputs
private fun responseNum(message: String, max: Int): Int{
    println(message)
    var response: Int
    do {
        response = readln().toIntOrNull() ?: -1
        if (response <= 0 || response > max){ println("Introduce un número valido")} else{ break }
    }while (true) // minimos y maximos del tablero
    return response
}

private fun responseYesOrNot(message: String): Boolean{
    println("$message (s/n):")
    var response: String
    do {
        response = readln().lowercase()
        if (response != "s" && response != "n"){ println("""Introduce "S" para si o introduce "N" para no""")} else{ break }
    }while (true) // minimos y maximos del tablero

    return response == "s"
}
// endregion