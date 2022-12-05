package Juegos

fun main(){
    var matriz = Array(25){IntArray(25)}
    generateFirstPosition(matriz)
    do {
        mostrarMatriz(matriz)
        val newMatriz = Array(25){IntArray(25)}
        for (i in matriz.indices){
            for (j in matriz[i].indices){
                if (matriz[i][j] == 0){
                    if (countVivas(intArrayOf(i,j), matriz) == 3){
                        newMatriz[i][j] = 1
                    }
                }else{
                    if (countVivas(intArrayOf(i,j), matriz) in 2..3){
                        newMatriz[i][j] = 1
                    }else newMatriz[i][j] = 0
                }
            }
        }
        println("\n")
        if (matriz.contentEquals(newMatriz)) break
        matriz = newMatriz
        Thread.sleep(2500)
    }while (true)
}

fun countVivas(pos: IntArray, matriz: Array<IntArray>): Int{
    var count = 0

    // region Centro
    if (pos[0] - 1 in matriz.indices && pos[1] in matriz.indices && matriz[pos[0] -1][pos[1]] == 1){
        count++
    } // arriba centro

    if (pos[0] - 1 in matriz.indices && pos[1] + 1 in matriz.indices && matriz[pos[0] -1][pos[1] + 1] == 1){
        count++
    } // arriba derecha

    if (pos[0] - 1 in matriz.indices && pos[1] - 1 in matriz.indices && matriz[pos[0] -1][pos[1] -1] == 1){
        count++
    } // arriba izquierda
    // endregion

    // region Abajo
    if (pos[0] + 1 in matriz.indices && pos[1] in matriz.indices && matriz[pos[0] + 1][pos[1]] == 1){
        count++
    } // abajo centro

    if (pos[0] + 1 in matriz.indices && pos[1] + 1 in matriz.indices && matriz[pos[0] + 1][pos[1] + 1] == 1){
        count++
    } // abajo derecha

    if (pos[0] + 1 in matriz.indices && pos[1] - 1 in matriz.indices && matriz[pos[0] + 1][pos[1] - 1] == 1){
        count++
    } // abajo izquierda
    // endregion

    // region Lados
    if (pos[0] in matriz.indices && pos[1] + 1 in matriz.indices && matriz[pos[0]][pos[1] + 1] == 1){
        count++
    } // abajo derecha

    if (pos[0] in matriz.indices && pos[1] - 1 in matriz.indices && matriz[pos[0]][pos[1] - 1] == 1) {
        count++
    }
    // endregion

    return count
}

fun generateFirstPosition(matriz: Array<IntArray>) {
    matriz[14][14] = 1
    matriz[15][15] = 1
    matriz[15][16] = 1
    matriz[14][16] = 1
    matriz[13][16] = 1
}

private fun mostrarMatriz(matriz: Array<IntArray>){
    for (i in matriz){
        for (j in i){
            print(if (j == 0){
                "$ANSI_BLACK_BACKGROUND 0 $ANSI_RESET"
            }else{
                "$ANSI_WHITE_BACKGROUND 1 $ANSI_RESET"
            })
        }
        println()
    }
}