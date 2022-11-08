package Juegos

import responseNum

//region Color
const val ANSI_YELLOW = "\u001B[33m" // moneda

const val ANSI_WHITE_BACKGROUND = "\u001B[47m" // nada-moneda
const val ANSI_BLUE_BACKGROUND = "\u001B[44m" // pared
const val ANSI_YELLOW_BACKGROUND = "\u001B[43m" // player
const val ANSI_RED_BACKGROUND = "\u001B[41m" // fantasma 1
const val ANSI_GREEN_BACKGROUND = "\u001B[42m" // fantasma 2
const val ANSI_PURPLE_BACKGROUND = "\u001B[45m" // fantasma 3
const val ANSI_BLACK_BACKGROUND = "\u001B[40m" // fantasma 4

const val ANSI_RESET = "\u001B[0m"
//endregion

var VidasPlayer = 3
var PuntosPlayer = 0

fun main (){
    val mapa = Array(16){IntArray(17){ 1 } }
    generateMapa(mapa)

    //Spawn player
    var playerPos = intArrayOf(11,8)
    mapa[playerPos[0]][playerPos[1]] = 3

    //Spawn fantasmas
    val fantasmasPos = Array(4){IntArray(2){0} }
    spawnFantasmas(fantasmasPos, mapa)

    do {
        partida@do {
            mostrarMapa(mapa)
            movimiento(mapa,playerPos, 3, false)
            for (i in fantasmasPos.indices){
                if (fantasmasPos[i][0] >= 0 || fantasmasPos[i][1] >= 0) {
                    movimiento(mapa,fantasmasPos[i],
                        when(i){
                        0 -> 4
                        1 -> 5
                        2 -> 6
                        3 -> 7
                        else -> throw IllegalArgumentException("Error")
                        }, true)
                    if (!isBlock(mapa,fantasmasPos[i],3..3)){
                        mostrarMapa(mapa)
                        println("$ANSI_RED_BACKGROUND\n TE PILLARON -1 VIDA \n$ANSI_RESET")
                        Thread.sleep(2_500)
                        break@partida
                    } // si esta cerca jugador lo mata
                }
            }
        }while (true)

        generateMapa(mapa)

        //Respawn Player
        playerPos = intArrayOf(11,8)
        mapa[playerPos[0]][playerPos[1]] = 3

        //Respawn Fantasmas
        spawnFantasmas(fantasmasPos, mapa)

        VidasPlayer--
    }while (VidasPlayer > 0 || PuntosPlayer >= 100)

    println("Los puntos obtenidos han sido $PuntosPlayer")
}

fun movimiento(mapa: Array<IntArray>, pos: IntArray, entidad:Int, isIa: Boolean){
    val incrementArray = IntArray(2) { 0 }
    moviminetoCruz(mapa, pos, entidad, 0..1, incrementArray, isIa)
    if (isIa) mapa[pos[0]][pos[1]] = 1 else mapa[pos[0]][pos[1]] = 0
    pos[0] = pos[0]+incrementArray[0]
    pos[1] = pos[1]+incrementArray[1]
    if (mapa[pos[0]][pos[1]] == 1 && !isIa) PuntosPlayer++
    mapa[pos[0]][pos[1]] = entidad
}

fun moviminetoCruz(mapa: Array<IntArray>, pos: IntArray, casillaInt:Int, casillasPermitidas: IntRange, incrementArray: IntArray, isIa:Boolean){
    if (isBlock(mapa, pos, casillasPermitidas)) return
    do {
        when(if (isIa)(0..3).random() else { responseNum("| 1-> Arriba | 2-> Abajo | 3-> Izq | 4-> Der |", 1, 4) - 1 }){
            0 -> if (pos[0] -1 in mapa.indices && pos[1] in mapa.indices) { incrementArray[0]-- }   // Arriba
            1 -> if (pos[0] +1 in mapa.indices && pos[1] in mapa.indices) { incrementArray[0]++ }   // Abajo
            2 -> if (pos[0] in mapa.indices && pos[1] -1 in mapa.first().indices) { incrementArray[1]-- }   // Izq
            3 -> if (pos[0] in mapa.indices && pos[1] +1 in mapa.first().indices) { incrementArray[1]++ }   // Der
            else -> throw IllegalArgumentException("Random Error")
        }
        if (mapa[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] == 8){ incrementArray[0] = 0; incrementArray[1] = 14; break}
        if (mapa[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] == 9){ incrementArray[0] = 0; incrementArray[1] = -14; break }
        if (mapa[pos[0]+incrementArray[0]][pos[1]+incrementArray[1]] in casillasPermitidas) break else if (!isIa) println("Introduce una dirección valida\n")
        incrementArray[0] = 0
        incrementArray[1] = 0
    }while (true)
}

fun isBlock(laberinto: Array<IntArray>, pos: IntArray, intCasilla:IntRange): Boolean{
    for (i in 0..3){
        when(i){
            0 -> if (pos[0] -1 in laberinto.indices && pos[1] in laberinto.indices && laberinto[pos[0] -1][pos[1]] in intCasilla) { return false }   // Arriba
            1 -> if (pos[0] +1 in laberinto.indices && pos[1] in laberinto.indices && laberinto[pos[0] +1][pos[1]] in intCasilla) { return false }   // Abajo
            2 -> if (pos[0] in laberinto.indices && pos[1] -1 in laberinto.indices && laberinto[pos[0]][pos[1] -1] in intCasilla) { return false }   // Izq
            3 -> if (pos[0] in laberinto.indices && pos[1] +1 in laberinto.indices && laberinto[pos[0]][pos[1] +1] in intCasilla) { return false }   // Der
            else -> throw IllegalArgumentException("Random Error")
        }
    }
    return true
}

private fun spawnFantasmas(posicionesFantasmas: Array<IntArray>, mapa: Array<IntArray>) {
    posicionesFantasmas[0] = intArrayOf(7, 7)
    posicionesFantasmas[1] = intArrayOf(8, 7)
    posicionesFantasmas[2] = intArrayOf(7, 9)
    posicionesFantasmas[3] = intArrayOf(8, 9)
    for (i in posicionesFantasmas.indices) {
        when (i) {
            0 -> mapa[posicionesFantasmas[0][0]][posicionesFantasmas[0][1]] = 4
            1 -> mapa[posicionesFantasmas[1][0]][posicionesFantasmas[1][1]] = 5
            2 -> mapa[posicionesFantasmas[2][0]][posicionesFantasmas[2][1]] = 6
            3 -> mapa[posicionesFantasmas[3][0]][posicionesFantasmas[3][1]] = 7
        }
    }
}

fun mostrarMapa(mapa: Array<IntArray>) {
    clear()
    println("Puntos: $PuntosPlayer")
    for(i in mapa){
        for (j in i){
            print(
                when(j){
                    0 -> "$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
                    1 -> "$ANSI_BLACK_BACKGROUND$ANSI_YELLOW ◦ $ANSI_RESET"
                    2 -> "$ANSI_BLUE_BACKGROUND   $ANSI_RESET"
                    3 -> "$ANSI_YELLOW_BACKGROUND   $ANSI_RESET"
                    4 -> "$ANSI_RED_BACKGROUND   $ANSI_RESET"
                    5 -> "$ANSI_GREEN_BACKGROUND   $ANSI_RESET"
                    6 -> "$ANSI_PURPLE_BACKGROUND   $ANSI_RESET"
                    7 -> "$ANSI_WHITE_BACKGROUND   $ANSI_RESET"
                    /*4 -> "$ANSI_RED_BACKGROUND \uD83D\uDC7B$ANSI_RESET"
                    5 -> "$ANSI_GREEN_BACKGROUND \uD83D\uDC7B$ANSI_RESET"
                    6 -> "$ANSI_PURPLE_BACKGROUND \uD83D\uDC7B$ANSI_RESET"
                    7 -> "$ANSI_WHITE_BACKGROUND \uD83D\uDC7B$ANSI_RESET"*/
                    else -> "$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
                }
            )
        }
        println()
    }
    println()
    mostrarVidas(VidasPlayer)
}

fun mostrarVidas(vidas: Int) {
    print("\t\t\t\t\t  ")
    for (i in 0 until vidas){
        print("❤")
    }
    println()
}

private fun clear() {
    for (i in 0..10) {
        println()
    }
}

fun generateMapa(mapa: Array<IntArray>) {
    mapa[0] = intArrayOf(1,	1,	1,	1,	1,	1,	1,	1,	2,	1,	1,	1,	1,	1,	1,	1,	1)
    mapa[1] = intArrayOf(1,	2,	2,	1,	2,	1,	2,	2,	2,	2,	2,	1,	2,	1,	2,	2,	1)
    mapa[2] = intArrayOf(1,	1,	1,	1,	2,	1,	1,	1,	2,	1,	1,	1,	2,	1,	1,	1,	1)
    mapa[3] = intArrayOf(2,	2,	2,	1,	2,	2,	2,	1,	2,	1,	2,	2,	2,	1,	2,	2,	2)
    mapa[4] = intArrayOf(0,	0,	2,	1,	2,	1,	1,	1,	1,	1,	1,	1,	2,	1,	2,	0,	0)
    mapa[5] = intArrayOf(0,	0,	2,	1,	2,	1,	1,	1,	1,	1,	1,	1,	2,	1,	2,	0,	0)
    mapa[6] = intArrayOf(2,	2,	2,	1,	2,	1,	2,	2,	0,	2,	2,	1,	2,	1,	2,	2,	2)
    mapa[7] = intArrayOf(8,	1,	1,	1,	1,	1,	2,	1,	0,	1,	2,	1,	1,	1,	1,	1,	9)
    mapa[8] = intArrayOf(8,	1,	1,	1,	1,	1,	2,	1,	0,	1,	2,	1,	1,	1,	1,	1,	9)
    mapa[9] = intArrayOf(2,	2,	2,	1,	2,	1,	2,	2,	2,	2,	2,	1,	2,	1,	2,	2,	2)
    mapa[10] = intArrayOf(0,	0,	2,	1,	2,	1,	1,	1,	1,	1,	1,	1,	2,	1,	2,	0,	0)
    mapa[11] = intArrayOf(0,	0,	2,	1,	2,	1,	1,	1,	1,	1,	1,	1,	2,	1,	2,	0,	0)
    mapa[12] = intArrayOf(2,	2,	2,	1,	2,	2,	2,	1,	2,	1,	2,	2,	2,	1,	2,	2,	2)
    mapa[13] = intArrayOf(1,	1,	1,	1,	2,	1,	1,	1,	2,	1,	1,	1,	2,	1,	1,	1,	1)
    mapa[14] = intArrayOf(1,	2,	2,	1,	2,	1,	2,	2,	2,	2,	2,	1,	2,	1,	2,	2,	1)
    mapa[15] = intArrayOf(1,	1,	1,	1,	1,	1,	1,	1,	2,	1,	1,	1,	1,	1,	1,	1,	1)
}
