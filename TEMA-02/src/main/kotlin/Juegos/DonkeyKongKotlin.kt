package Juegos

import responseNum
import kotlin.system.exitProcess

var lastCasillaInt = 0

private var VidasPlayer = 3
private var JugadaVivo = true

fun main(){
    val mapa = Array(17){IntArray(10){ 0 } }
    generateMapa(mapa)

    var playerPos = intArrayOf(15,9)
    mapa[playerPos[0]][playerPos[1]] = 3

    var barrilesPos = Array(8){ IntArray(2){ -1 } } // 8 barriles max
    var barrilesDireccion = IntArray( barrilesPos.size ){ 1 } // Direccion de los barriles 1 o -1
    var barrilesLastCasillaInt = IntArray( barrilesPos.size ){ 0 } // Ultima casilla pisada por los barriles

    do {
        JugadaVivo = true
        partida@do {
            mostrarMapa(mapa)
            val salto = playerMovimiento(mapa, playerPos, false)

            if(lastCasillaInt == 7){ mostrarMapa(mapa); println("VICTORIA!!!!!!"); Thread.sleep(2_500);exitProcess(0) }

            // Barriles
            //Movimiento
            repeat(2){
                for (i in barrilesPos.indices){
                    if (barrilesPos[i][0] != -1 && barrilesPos[i][1] != -1){
                        barrilMovimeinto(mapa, barrilesPos, barrilesDireccion, barrilesLastCasillaInt, i)
                        if (!JugadaVivo) break
                    }
                }
                mostrarMapa(mapa);Thread.sleep(250)
            }
            if (!JugadaVivo) break
            //Spawn
            spawnBarril(mapa, barrilesPos, 25)
            // Down
            if (salto) {mostrarMapa(mapa);Thread.sleep(250); playerMovimiento(mapa,playerPos, salto)}
        }while (JugadaVivo)
        generateMapa(mapa)

        playerPos = intArrayOf(15,9)
        mapa[playerPos[0]][playerPos[1]] = 3

        barrilesPos = Array(8){ IntArray(2){ -1 } } // 8 barriles max
        barrilesDireccion = IntArray( barrilesPos.size ){ 1 } // Direccion de los barriles 1 o -1
        barrilesLastCasillaInt = IntArray( barrilesPos.size ){ 0 } // Ultima casilla pisada por los barriles
    }while (VidasPlayer > 0)
}

fun dead() {
    VidasPlayer--
    JugadaVivo = false
    println("$ANSI_RED_BACKGROUND\nNooo te comiste un barril\n$ANSI_RESET")
    Thread.sleep(2_500)
}

fun barrilMovimeinto(mapa: Array<IntArray>, pos: Array<IntArray>, direction: IntArray, lassCasillaIntBarril:IntArray, index:Int ) {
    mapa[pos[index][0]][pos[index][1]] = lassCasillaIntBarril[index]

    if (pos[index][0] == 15 && pos[index][1] + direction[index] == 9){ pos[index] = intArrayOf(-1,-1); return } // remove
    if (mapa[pos[index][0]][pos[index][1]+ direction[index]] == 3){ dead(); return } //HitPlayer

    pos[index][1] = pos[index][1] + direction[index]
    if (mapa[pos[index][0] +1][pos[index][1]] == 5){
        pos[index][0] += 3
        direction[index] *= -1
    } // si tiene que cambier de fila

    lassCasillaIntBarril[index] = mapa[pos[index][0]][pos[index][1]]
    mapa[pos[index][0]][pos[index][1]] = 4
}

fun spawnBarril(mapa: Array<IntArray>, barrilesPos: Array<IntArray>, probability: Int) {
    if ((0..100).random() <= probability){
        for (i in barrilesPos.indices){
            if (barrilesPos[i][0] == -1 && barrilesPos[i][1] == -1) // Barril no activo
            {
                barrilesPos[i] = intArrayOf(3,2)
                mapa[barrilesPos[i][0]][barrilesPos[i][1]] = 4
                break
            }
        }
    }
}

private fun playerMovimiento(mapa: Array<IntArray>, pos: IntArray, isJumping: Boolean): Boolean{
    val incrementArray = IntArray(2) { 0 }
    var salto = false
    do {
        when(if(!isJumping)responseNum("| 1-> Izq | 2-> Der | 3-> Arriba | 4-> Abajo |", 1, 4) - 1 else 3){
            0 -> if (pos[0] in mapa.indices && pos[1] -1 in mapa.first().indices && mapa[pos[0] +1][pos[1] -1] in 1..2) { incrementArray[1]--; break }   // Izq
            1 -> if (pos[0] in mapa.indices && pos[1] +1 in mapa.first().indices && mapa[pos[0] +1][pos[1] +1] in 1..2) { incrementArray[1]++; break }   // Der
            2 -> if (pos[0] -1 in mapa.indices && pos[1] in mapa.first().indices && mapa[pos[0] -1][pos[1]] != 4){
                incrementArray[0]--
                if ( mapa[pos[0] +incrementArray[0]][pos[1]] == 0){ salto=true }
                break
            }
            3 -> if (pos[0] +1 in mapa.indices && pos[1] in mapa.first().indices ) {
                if (mapa[pos[0] +1][pos[1]] == 0 || mapa[pos[0] +1][pos[1]] == 2){
                    incrementArray[0]++; break
                }else if (mapa[pos[0] +1][pos[1]] == 4){
                    dead(); return false
                }
            }
            else -> throw IllegalArgumentException("Random Error")
        }
    }while (true)

    mapa[pos[0]][pos[1]] = lastCasillaInt

    pos[0] = pos[0]+incrementArray[0]
    pos[1] = pos[1]+incrementArray[1]

    lastCasillaInt = mapa[pos[0]][pos[1]]
    mapa[pos[0]][pos[1]] = 3
    return salto
}

private fun mostrarMapa(mapa: Array<IntArray>) {
    clear()
    for(i in mapa){
        for (j in i){
            print(
                when(j){
                    0 -> "$ANSI_WHITE_BACKGROUND   $ANSI_RESET"
                    1 -> "$ANSI_RED_BACKGROUND   $ANSI_RESET"
                    2 -> "$ANSI_BLUE_BACKGROUND   $ANSI_RESET"
                    3 -> "$ANSI_GREEN_BACKGROUND   $ANSI_RESET"
                    4 -> "$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
                    5 -> "$ANSI_WHITE_BACKGROUND   $ANSI_RESET"
                    6 -> "$ANSI_PURPLE_BACKGROUND   $ANSI_RESET"
                    7 -> "$ANSI_YELLOW_BACKGROUND   $ANSI_RESET"
                    else -> "$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
                }
            )
        }
        println()
    }
    println()
    mostrarVidas(VidasPlayer)
}

private fun generateMapa(mapa: Array<IntArray>) {
    mapa[0] = intArrayOf(0,	0,	0,	0,	0,	0,	0,	0,	0,	0)
    mapa[1] = intArrayOf(0,	0,	0,	2,	7,	0,	0,	0,	0,	0)
    mapa[2] = intArrayOf(6,	6,	0,	2,	1,	0,	0,	0,	0,	0)
    mapa[3] = intArrayOf(6,	6,	0,	2,	0,	0,	0,	2,	0,	0)
    mapa[4] = intArrayOf(1,	1,	1,	1,	1,	1,	1,	2,	1,	5)
    mapa[5] = intArrayOf(0,	0,	0,	0,	0,	0,	0,	2,	0,	0)
    mapa[6] = intArrayOf(0,	0,	2,	0,	0,	0,	0,	2,	0,	0)
    mapa[7] = intArrayOf(5,	1,	2,	1,	1,	1,	1,	1,	1,	1)
    mapa[8] = intArrayOf(0,	0,	2,	0,	0,	0,	0,	0,	0,	0)
    mapa[9] = intArrayOf(0,	0,	2,	0,	0,	0,	0,	2,	0,	0)
    mapa[10] = intArrayOf(1,	1,	1,	1,	1,	1,	1,	2,	1,	5)
    mapa[11] = intArrayOf(0,	0,	0,	0,	0,	0,	0,	2,	0,	0)
    mapa[12] = intArrayOf(0,	0,	2,	0,	0,	0,	0,	2,	0,	0)
    mapa[13] = intArrayOf(5,	1,	2,	1,	1,	1,	1,	1,	1,	1)
    mapa[14] = intArrayOf(0,	0,	2,	0,	0,	0,	0,	0,	0,	0)
    mapa[15] = intArrayOf(0,	0,	2,	0,	0,	0,	0,	0,	0,	0)
    mapa[16] = intArrayOf(1,	1,	1,	1,	1,	1,	1,	1,	1,	1)
}