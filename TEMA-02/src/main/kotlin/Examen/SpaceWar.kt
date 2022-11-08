package Examen

import responseNum

const val ANSI_BLACK_BACKGROUND = "\u001B[40m"

const val ANSI_BLACK = "\u001B[30m"
const val ANSI_WHITE = "\u001B[37m"

const val ANSI_RED_BACKGROUND = "\u001B[41m"
const val ANSI_GREEN_BACKGROUND = "\u001B[42m"

const val ANSI_RESET = "\u001B[0m"

fun main(){
    val espacio = Array(7){ IntArray(7){ 0 } }

    // Pedir Enemigos
    val countEnemigos = responseNum("Cuantos Enemigos quieres", 10, 25)

    // Spawn Player
    var escudoPlayer = 10
    var posPlayer = spawnEnte(espacio, 1)

    // Spawn Enemigos
    spawnEnte(espacio, countEnemigos, (2..5))

    mostrarEspacio(espacio)

    val MAX_TIME = 2_000
    var countTime = 0
    do {
        if (enemigosAround(posPlayer, espacio) > 0) disparoPlayer(posPlayer, espacio)
        espacio[posPlayer[0]][posPlayer[1]] = 0
        posPlayer = moverPlayer(espacio)
        espacio[posPlayer[0]][posPlayer[1]] = 1
        mostrarEspacio(espacio)
        if ((0..100).random() <= 10){ escudoPlayer-- }
        Thread.sleep(250)
        countTime += 100
    }while (escudoPlayer > 0 && countTime < MAX_TIME)

    // Final

    if (escudoPlayer > 0){
        println("Player a sobrevivido con un escudo al $escudoPlayer")
    }else{
        println("Player no ha sobrevivido")
    }
    println("Le quedaron ${enemigosEspacio(espacio)} enemigos")
}

fun enemigosEspacio(espacio: Array<IntArray>): Int {
    var count = 0
    for (i in espacio.indices){
        for (j in espacio[i].indices){
            if (espacio[i][j] in 2..5){
                count++
            }
        }
    }
    return count
}

fun moverPlayer(espacio: Array<IntArray>): Array<Int> {
    var randomPos: Array<Int>
    do {
        randomPos = arrayOf((espacio.indices).random(), (espacio.indices).random())
    }while (espacio[randomPos[0]][randomPos[1]] != 0)
    return randomPos
}

fun cloneMatriz(matriz: Array<IntArray>): Array<IntArray>{
    val newMatriz = Array(matriz.size){IntArray(matriz[0].size)}
    for (i in matriz.indices){
        for (j in matriz[i].indices){
            newMatriz[i][j] = matriz[i][j]
        }
    }
    return newMatriz
}

fun enemigosAround(pos: Array<Int>, espacio: Array<IntArray>): Int{
    var countEnemigos = 0

    // region Centro
    if (pos[0] - 1 in espacio.indices && pos[1] in espacio.indices && espacio[pos[0] -1][pos[1]] in 2..5){
        countEnemigos++
    } // arriba centro

    if (pos[0] - 1 in espacio.indices && pos[1] + 1 in espacio.indices && espacio[pos[0] -1][pos[1] + 1] in 2..5){
        countEnemigos++
    } // arriba derecha

    if (pos[0] - 1 in espacio.indices && pos[1] - 1 in espacio.indices && espacio[pos[0] -1][pos[1] -1] in 2..5){
        countEnemigos++
    } // arriba izquierda
    // endregion

    // region Abajo
    if (pos[0] + 1 in espacio.indices && pos[1] in espacio.indices && espacio[pos[0] + 1][pos[1]] in 2..5){
        countEnemigos++
    } // abajo centro

    if (pos[0] + 1 in espacio.indices && pos[1] + 1 in espacio.indices && espacio[pos[0] + 1][pos[1] + 1] in 2..5){
        countEnemigos++
    } // abajo derecha

    if (pos[0] + 1 in espacio.indices && pos[1] - 1 in espacio.indices && espacio[pos[0] + 1][pos[1] - 1] in 2..5){
        countEnemigos++
    } // abajo izquierda
    // endregion

    // region Lados
    if (pos[0] in espacio.indices && pos[1] + 1 in espacio.indices && espacio[pos[0]][pos[1] + 1] in 2..5){
        countEnemigos++
    } // abajo derecha

    if (pos[0] in espacio.indices && pos[1] - 1 in espacio.indices && espacio[pos[0]][pos[1] - 1] in 2..5) {
        countEnemigos++
    }
    // endregion

    return countEnemigos
}

fun disparoPlayer(pos: Array<Int>, espacio: Array<IntArray>){
    // region Centro
    if (pos[0] - 1 in espacio.indices && pos[1] in espacio.indices && espacio[pos[0] -1][pos[1]] in 2..5){
        espacio[pos[0] -1][pos[1]] -= 1
        if (espacio[pos[0] -1][pos[1]] == 1){
            espacio[pos[0] -1][pos[1]] = 0
        }
    } // arriba centro

    if (pos[0] - 1 in espacio.indices && pos[1] + 1 in espacio.indices && espacio[pos[0] -1][pos[1] + 1] in 2..5){
        espacio[pos[0] -1][pos[1] + 1] -= 1
        if (espacio[pos[0] -1][pos[1] + 1] == 1){
            espacio[pos[0] -1][pos[1] + 1] = 0
        }
    } // arriba derecha

    if (pos[0] - 1 in espacio.indices && pos[1] - 1 in espacio.indices && espacio[pos[0] -1][pos[1] -1] in 2..5){
        espacio[pos[0] -1][pos[1] - 1] -= 1
        if (espacio[pos[0] -1][pos[1] - 1] == 1){
            espacio[pos[0] -1][pos[1] - 1] = 0
        }
    } // arriba izquierda
    // endregion

    // region Abajo
    if (pos[0] + 1 in espacio.indices && pos[1] in espacio.indices && espacio[pos[0] + 1][pos[1]] in 2..5){
        espacio[pos[0] + 1][pos[1]] -= 1
        if (espacio[pos[0] + 1][pos[1]] == 1){
            espacio[pos[0] + 1][pos[1]] = 0
        }
    } // abajo centro

    if (pos[0] + 1 in espacio.indices && pos[1] + 1 in espacio.indices && espacio[pos[0] + 1][pos[1] + 1] in 2..5){
        espacio[pos[0] + 1][pos[1] + 1] -= 1
        if (espacio[pos[0] + 1][pos[1] + 1] == 1){
            espacio[pos[0] + 1][pos[1] + 1] = 0
        }
    } // abajo derecha

    if (pos[0] + 1 in espacio.indices && pos[1] - 1 in espacio.indices && espacio[pos[0] + 1][pos[1] - 1] in 2..5){
        espacio[pos[0] + 1][pos[1] - 1] -= 1
        if (espacio[pos[0] + 1][pos[1] - 1] == 1){
            espacio[pos[0] + 1][pos[1] - 1] = 0
        }
    } // abajo izquierda
    // endregion

    // region Lados
    if (pos[0] in espacio.indices && pos[1] + 1 in espacio.indices && espacio[pos[0]][pos[1] + 1] in 2..5){
        espacio[pos[0]][pos[1] + 1] -= 1
        if (espacio[pos[0]][pos[1] + 1] == 1){
            espacio[pos[0]][pos[1] + 1] = 0
        }
    } // abajo derecha

    if (pos[0] in espacio.indices && pos[1] - 1 in espacio.indices && espacio[pos[0]][pos[1] - 1] in 2..5) {
        espacio[pos[0]][pos[1] - 1] -= 1
        if (espacio[pos[0]][pos[1] - 1] == 1){
            espacio[pos[0]][pos[1] - 1] = 0
        }
    }
    // endregion

}

fun mostrarEspacio(espacio: Array<IntArray>) {
    for (i in espacio){
        for (j in i){
            print(
                when(j){
                    0 -> "$ANSI_BLACK_BACKGROUND$ANSI_WHITE $j $ANSI_RESET"
                    1 -> "$ANSI_GREEN_BACKGROUND$ANSI_BLACK $j $ANSI_RESET"
                    in 2..5 -> "$ANSI_RED_BACKGROUND$ANSI_BLACK $j $ANSI_RESET"
                    else -> "$ANSI_BLACK_BACKGROUND$ANSI_WHITE $j $ANSI_RESET"
                }
            )
        }
        println()
    }
    println()
}

fun spawnEnte(arrays: Array<IntArray>, count: Int, intCasilla: IntRange) {
    for (i in 0 until count){
        spawnEnte(arrays, intCasilla.random())
    }
}

fun spawnEnte(arrays: Array<IntArray>, intCasilla: Int): Array<Int>{
    var randomPos: Array<Int>
    do {
        randomPos = arrayOf((arrays.indices).random(), (arrays.indices).random())
    }while (arrays[randomPos[0]][randomPos[1]] != 0)
    arrays[randomPos[0]][randomPos[1]] = intCasilla
    return randomPos
}
