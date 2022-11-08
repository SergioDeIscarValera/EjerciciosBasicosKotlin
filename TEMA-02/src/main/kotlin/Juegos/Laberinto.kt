import kotlin.system.exitProcess

/**
 * Juego del minotauro laberinto
 */

const val BRANCH_PROBABILITY = 15
const val BRANCH_DEAD_PROBABILITY = 0 // 1%
const val ANSI_RED_BACKGROUND = "\u001B[41m"
const val ANSI_GREEN_BACKGROUND = "\u001B[42m"
const val ANSI_BLUE_BACKGROUND = "\u001B[44m"
const val ANSI_PURPLE_BACKGROUND = "\u001B[45m"

fun main(){
    // Introducir tamaño del laberinto
    val size = responseNum("Introduce el tamaño del mapa", 10, 150)
    val laberinto = Array(size){ IntArray(size){ 0 } } // 0 -> sin asignar, 1 -> pasillo, 2 -> pared

    // Generar el pasillo
    val initialPos = IntArray(2){ 0 } // pos[0] -> row | pos[1] -> column
    val initialTime = System.currentTimeMillis()
    generatePasillo(laberinto, initialPos)
    val finalTime = System.currentTimeMillis()

    // Mostrar laberinto
    mostrarLaberinto(laberinto)
    println("Ha tardado: ${finalTime-initialTime}ms")

    // Spawn Minotauros
    val minotaurosPos = Array(size){ IntArray(2){ -1 } }
    when(size){
        in 0..10 -> spawnEnte(laberinto, 1, minotaurosPos, 3)
        in 11..20 -> spawnEnte(laberinto, 2, minotaurosPos, 3)
        in 21..30 -> spawnEnte(laberinto, 10, minotaurosPos, 3)
        else -> return
    }

    // Spawn Player
    laberinto[0][0] = 4

    // Spawn objetivo
    spawnEnte(laberinto,1, null, 5)

    // JUEGO
    var count = 0
    val playerPos = intArrayOf(0,0)
    do{
        for (i in minotaurosPos){
            if (i[0] >= 0 || i[1] >= 0) {
                if (!isBlock(laberinto,i,4)){
                    dead(laberinto,playerPos, null)
                } // si esta cerca jugador lo mata
                movimiento(laberinto,i,3, true)
            }
        }
        mostrarLaberinto(laberinto)
        count++
    }while (movimiento(laberinto, playerPos, 4, false))
}

// region Juego
fun movimiento(laberinto: Array<IntArray>, pos: IntArray, intCasilla: Int, isIa: Boolean): Boolean {
    val incrementArray = IntArray(2) { 0 }
    if(!moviminetoCruz(laberinto, pos, 1, incrementArray, isIa)) { return false }
    laberinto[pos[0]][pos[1]] = 1
    laberinto[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] = intCasilla
    pos[0] = pos[0]+incrementArray[0]
    pos[1] = pos[1]+incrementArray[1]
    return true
}

private fun spawnEnte(laberinto: Array<IntArray>, count: Int, matriz: Array<IntArray>?, intCasilla: Int) {
    for (i in 0 until count){
        var randomPos: Array<Int>
        do {
            randomPos = arrayOf((laberinto.indices).random(), (laberinto.indices).random())
        }while (laberinto[randomPos[0]][randomPos[1]] != 1)

        if(matriz != null) matriz[i] = randomPos.toIntArray()
        laberinto[randomPos[0]][randomPos[1]] = intCasilla
    }
}
// endregion

fun randomNewPos(laberinto: Array<IntArray>, pos: IntArray): Boolean{
    val incrementArray = IntArray(2) { 0 }
    if(!moviminetoCruz(laberinto, pos, 0, incrementArray, true)) { return false }

    blockPos(laberinto, pos, incrementArray[0], incrementArray[1])

    pos[0] += incrementArray[0]
    pos[1] += incrementArray[1]
    return true
}

fun moviminetoCruz(laberinto: Array<IntArray>, pos: IntArray, intCasilla: Int, incrementArray: IntArray, isIa:Boolean): Boolean{
    if (isBlock(laberinto, pos, intCasilla)) return false
    do {
        when(if (isIa)(0..3).random() else { responseNum("| 1-> Arriba | 2-> Abajo | 3-> Izq | 4-> Der |", 1, 4) - 1 }){
            0 -> if (pos[0] -1 in laberinto.indices && pos[1] in laberinto.indices) { incrementArray[0]-- }   // Arriba
            1 -> if (pos[0] +1 in laberinto.indices && pos[1] in laberinto.indices) { incrementArray[0]++ }   // Abajo
            2 -> if (pos[0] in laberinto.indices && pos[1] -1 in laberinto.indices) { incrementArray[1]-- }   // Izq
            3 -> if (pos[0] in laberinto.indices && pos[1] +1 in laberinto.indices) { incrementArray[1]++ }   // Der
            else -> throw IllegalArgumentException("Random Error")
        }
        if (!isIa && laberinto[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] == 5){ println("HAS GANADO!!!"); exitProcess(0) }
        if (isIa && laberinto[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] == 4){ dead(laberinto, pos, incrementArray) }
        if (laberinto[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] == intCasilla) break else if (!isIa) println("Introduce una dirección valida\n")
        incrementArray[0] = 0
        incrementArray[1] = 0
    }while (true)
    return true
}

private fun dead(laberinto: Array<IntArray>, pos: IntArray, incrementArray: IntArray?) {
    if (incrementArray == null){
        laberinto[pos[0]][pos[1]]
    }else{
        laberinto[pos[0] + incrementArray[0]][pos[1] + incrementArray[1]] = 6
    }

    mostrarLaberinto(laberinto)
    println("Nooo te mato el minotauro")
    exitProcess(0)
}

fun isBlock(laberinto: Array<IntArray>, pos: IntArray, intCasilla:Int): Boolean{
    for (i in 0..3){
        when(i){
            0 -> if (pos[0] -1 in laberinto.indices && pos[1] in laberinto.indices && laberinto[pos[0] -1][pos[1]] == intCasilla) { return false }   // Arriba
            1 -> if (pos[0] +1 in laberinto.indices && pos[1] in laberinto.indices && laberinto[pos[0] +1][pos[1]] == intCasilla) { return false }   // Abajo
            2 -> if (pos[0] in laberinto.indices && pos[1] -1 in laberinto.indices && laberinto[pos[0]][pos[1] -1] == intCasilla) { return false }   // Izq
            3 -> if (pos[0] in laberinto.indices && pos[1] +1 in laberinto.indices && laberinto[pos[0]][pos[1] +1] == intCasilla) { return false }   // Der
            else -> throw IllegalArgumentException("Random Error")
        }
    }
    return true
}

// region Generate laberinto
fun generatePasillo(laberinto: Array<IntArray>, pos: IntArray) {
    //Posición inicial 0,0
    do {
        laberinto[pos[0]][pos[1]] = 1
        /*if(laberinto.size <= 15){mostrarLaberinto(laberinto); Thread.sleep(300)}*/
        if((0..100).random() <= BRANCH_DEAD_PROBABILITY){ break }
    }while (randomNewPos(laberinto, pos) && (pos[0] != laberinto.indices.last || pos[1] != laberinto.indices.last) ) //Posicion final/objetivo laberinto.indices.last,laberinto.indices.last
    laberinto[pos[0]][pos[1]] = 1
}

fun blockPos(laberinto: Array<IntArray>, pos: IntArray, row:Int, colum:Int) {
    // Arriba row-1
    if ((pos[0]-1 in laberinto.indices) && (pos[1] in laberinto.indices) &&
        pos[0]-1 != pos[0]+row && laberinto[pos[0]-1][pos[1]] != 1){
        if ((0..100).random() <= BRANCH_PROBABILITY){ generatePasillo(laberinto, clone(pos)) }
        else laberinto[pos[0]-1][pos[1]] = 2
    }

    // Abajo row+1
    if ((pos[0]+1 in laberinto.indices) && (pos[1] in laberinto.indices) &&
        pos[0]+1 != pos[0]+row && laberinto[pos[0]+1][pos[1]] != 1){
        if ((0..100).random() <= BRANCH_PROBABILITY){ generatePasillo(laberinto, clone(pos)) }
        else laberinto[pos[0]+1][pos[1]] = 2
    }

    // Izq column-1
    if ((pos[0] in laberinto.indices) && (pos[1]-1 in laberinto.indices) &&
        pos[1]-1 != pos[1]+colum && laberinto[pos[0]][pos[1]-1] != 1){
        if ((0..100).random() <= BRANCH_PROBABILITY){ generatePasillo(laberinto, clone(pos)) }
        else laberinto[pos[0]][pos[1]-1] = 2
    }

    // Der column+1
    if ((pos[0] in laberinto.indices) && (pos[1]+1 in laberinto.indices) &&
        pos[1]+1 != pos[1]+colum && laberinto[pos[0]][pos[1]+1] != 1){
        if ((0..100).random() <= BRANCH_PROBABILITY){ generatePasillo(laberinto, clone(pos)) }
        else laberinto[pos[0]][pos[1]+1] = 2
    }
}
// endregion

// region Output
fun mostrarLaberinto(laberinto: Array<IntArray>){
    for (i in laberinto){
        for (j in i){
            print(
                when(j){
                    1 -> "$ANSI_WHITE_BACKGROUND$ANSI_BLACK $j $ANSI_RESET"
                    3 -> "$ANSI_RED_BACKGROUND$ANSI_BLACK $j $ANSI_RESET"
                    4 -> "$ANSI_GREEN_BACKGROUND $j $ANSI_RESET"
                    5 -> "$ANSI_BLUE_BACKGROUND $j $ANSI_RESET"
                    6 -> "$ANSI_PURPLE_BACKGROUND $j $ANSI_RESET"
                    else -> "$ANSI_BLACK_BACKGROUND$ANSI_WHITE $j $ANSI_RESET"
                }
            )
        }
        println()
    }
    println()
}
// endregion

// region Input
fun responseNum(menssage: String, min: Int, max: Int): Int {
    println(menssage)
    var response: Int
    do {
        response = readln().toIntOrNull() ?: -1
        if (response !in min..max || response == -1){ println("Introduce un número valido") } else{ break }
    }while (true)
    return response
}
// endregion