package Examen

import Juegos.ANSI_BLUE_BACKGROUND
import Juegos.ANSI_WHITE_BACKGROUND

fun main(){
    val matriz = Array(6){IntArray(8)}
    generateRandomMatriz(matriz, 0..matriz.indices.last)
    mostrarMatriz(matriz)
    llover(matriz)
    println("Hay un total de ${countAgua(matriz)} gotas de agua")
    mostrarMatriz(matriz)
}

fun llover(matriz: Array<IntArray>) {
    for (i in matriz.indices){
        if (i in matriz.indices.first+1 until matriz.indices.last){
            for (j in matriz[i].indices){
                if (matriz[i][j] == 0 && tienePared(matriz, 0,i, j) && tienePared(matriz, 5,i, j)){
                    matriz[i][j] = 2
                }
            }
        }
    }
}

fun tienePared(matriz: Array<IntArray>, last: Int, row: Int, column: Int): Boolean {
    var count = 0
    if (last < row){
        for (i in last..row){
            if (matriz[i][column] == 1) count++
        }
    }else{
        for (i in row..last){
            if (matriz[i][column] == 1) count++
        }
    }

    return count >= 1
}

fun countAgua(matriz: Array<IntArray>):Int{
    var count = 0
    for (i in matriz){
        for (j in i){
            if(j == 2) count++
        }
    }
    return count
}

fun mostrarMatriz(matriz: Array<IntArray>) {
    for (i in matriz[0].indices.last downTo matriz[0].indices.first){
        for (j in matriz.indices){
            print( when(matriz[j][i]){
                1 -> "$ANSI_BLACK_BACKGROUND  $ANSI_RESET"
                2 -> "$ANSI_BLUE_BACKGROUND  $ANSI_RESET"
                else -> "$ANSI_WHITE_BACKGROUND  $ANSI_RESET"
            })
        }
        println()
    }
    println()
}

fun generateRandomMatriz(array: Array<IntArray>, range: IntRange) {
    for(i in array.indices){
        for (j in 0..(range).random()){
            array[i][j] = 1
        }
    }
}
