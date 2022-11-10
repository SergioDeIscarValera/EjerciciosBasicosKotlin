package Juegos

import responseNum
import kotlin.system.exitProcess

private var PlayerPos = IntArray(2)
private var PlayerPoints = 0
private var PlayerVidas = 3
private val Enemigos = Array(30){ Enemigo(intArrayOf(-1,-1).toTypedArray(), 3) }
private var EnemigosMovimientoCount = 1
private var EnemigosDireccion = 1 // 1 | -1
private val ListDisparos = mutableListOf<Disparo>()
private var Dificultad = 0

fun main(){
    val mapa = Array(16){ IntArray(24) { 0 } }
    Dificultad = choseDificultad()
    generarMapa(mapa)
    do {
        println("Puntos: $PlayerPoints")
        mostrarMapa(mapa)
        mostrarVidas(PlayerVidas)
        turnoPlayer(mapa)

        calcularDisparos(mapa)

        turnoEnemigos()

        clearEnemigosArea(mapa)
        mostrarDisparos(mapa)
        mostrarEnemigos(mapa)
    }while (PlayerVidas > 0 && enemigosConVida())

    mostrarMapa(mapa)
    println("$ANSI_BLUE_BACKGROUND\nHas acabado con un total de $PlayerPoints puntos.\n\n$ANSI_RESET")
}

fun enemigosConVida(): Boolean {
    var boolean = false
    for (i in Enemigos){
        if (i.vida>0){
            boolean = true
            break
        }
    }
    return boolean
}

fun calcularDisparos(mapa: Array<IntArray>) {
    repeat(2){
        movimientoDisparos(mapa)
    }
    //mostrarDisparos(mapa)
}

fun mostrarDisparos(mapa: Array<IntArray>) {
    for (i in ListDisparos){
        mapa[i.pos[0]][i.pos[1]] = 5
    }
}

fun turnoEnemigos() {
    //Mover
    if((0..100).random() <= Dificultad )moverEnemigos()
    //Disparo de los de abajo
    for (i in ultimaLineaEnemigos()){
        if ((0..100).random() <= Dificultad) disparoFun(i.pos.toIntArray(), 1)
    }
}

private fun movimientoDisparos(mapa: Array<IntArray>) {
    val listToRemove = mutableListOf<Disparo>()
    for (i in ListDisparos.indices) {
        if (ListDisparos[i].pos[0] != 0 && ListDisparos[i].pos[0] != 15) {
            var remove = false
            if (mapa[ListDisparos[i].pos[0]][ListDisparos[i].pos[1]] in 5..7) mapa[ListDisparos[i].pos[0]][ListDisparos[i].pos[1]] = 0
            when (mapa[ListDisparos[i].pos[0] + ListDisparos[i].direccion][ListDisparos[i].pos[1]]) {
                1 -> {
                    PlayerVidas--
                    println("$ANSI_RED_BACKGROUND\n Te han dado!!! \n\n$ANSI_RESET")
                    Thread.sleep(2_500)
                    remove = true
                }    // Player
                2 -> {
                    val index = enemigoIndex(arrayOf(ListDisparos[i].pos[0] + ListDisparos[i].direccion, ListDisparos[i].pos[1]))
                    if (Enemigos[index].vida > 0){
                        Enemigos[index].vida--
                        println("Enemgio dañado")
                        PlayerPoints += 2 * Dificultad
                        remove = true
                    }else{
                        ListDisparos[i].pos[0] += ListDisparos[i].direccion
                    }
                }    // Enemigo
                in 8..9 -> {
                    mapa[ListDisparos[i].pos[0] + ListDisparos[i].direccion][ListDisparos[i].pos[1]] += 1; remove = true
                } // Pared
                10 -> {
                    mapa[ListDisparos[i].pos[0] + ListDisparos[i].direccion][ListDisparos[i].pos[1]] = 0; remove = true
                } // Pared muerta
                else -> ListDisparos[i].pos[0] += ListDisparos[i].direccion
            }
            if (remove) listToRemove.add(ListDisparos[i])
        }else{
            listToRemove.add(ListDisparos[i])
        }
    }
    for (i in listToRemove){
        ListDisparos.remove(i)
    }
}

private fun ultimaLineaEnemigos(): Array<Enemigo> {
    val listPosition = mutableListOf<Array<Int>>()
    for (i in 0..9){
        if (Enemigos[i].vida > 0)
            listPosition.add(Enemigos[i].pos)
    }
    for (i in 10..29){
        for (j in listPosition){
            if (Enemigos[i].pos[1] == j[1] && Enemigos[i].pos[0] > j[0] && Enemigos[i].vida > 0){
                listPosition[listPosition.indexOf(j)] = Enemigos[i].pos
            }
        }
    }
    val newArray = Array(listPosition.size){ Enemigo(intArrayOf(-1,-1).toTypedArray(), 3) }
    for (i in newArray.indices){
        newArray[i] = Enemigos[enemigoIndex(listPosition[i])]
    }
    return newArray
}

private fun moverEnemigos() {
    if (EnemigosMovimientoCount != 5) { // ir en direccion
        for (i in Enemigos.indices) {
            Enemigos[i].pos[1] += EnemigosDireccion
            if (Enemigos[i].pos[0] == 11 && Enemigos[i].pos[1] == 23) {
                println(" - DERROTA - "); Thread.sleep(2_500);exitProcess(0)
            }
        }
        EnemigosMovimientoCount++
    } else { // Mover abajo y cambier direccion
        for (i in Enemigos.indices) {
            Enemigos[i].pos[0] += 1
        }
        EnemigosDireccion *= -1
        EnemigosMovimientoCount = 0
    }
}

private fun mostrarEnemigos(mapa: Array<IntArray>){
    for(i in Enemigos.indices){
        mapa[Enemigos[i].pos[0]][Enemigos[i].pos[1]] = 2
    }
}

fun turnoPlayer(mapa: Array<IntArray>) {
    when(responseAction("Quieres disparar 'D', quieres moverte 'M' o no hacer nada 'N': ", 'M', 'D', 'N')){
        1   -> moverPlayer(mapa)
        -1  -> disparoFun(intArrayOf(PlayerPos[0]-1,PlayerPos[1]), -1)
    }

    clearPlayerArea(mapa)
    spawnPlayer(mapa)
}

private fun disparoFun(pos: IntArray, direccion: Int) {
    ListDisparos.add(Disparo(pos.toTypedArray(),direccion))
}

private fun moverPlayer(mapa: Array<IntArray>) {
    do {
        when(responseNum("| 1-> Izq | 2-> Der |", 1, 2) -1){
            0 -> if (PlayerPos[0] in mapa.indices && PlayerPos[1] -2 in mapa.first().indices){ PlayerPos[1] -= 1; break}
            1 -> if (PlayerPos[0] in mapa.indices && PlayerPos[1] +2 in mapa.first().indices){ PlayerPos[1] += 1; break}
        }
    }while (true)
}

private fun responseAction(text: String, trueChar: Char, falseChar: Char, nothingChar: Char): Int {
    println(text)
    do {
        val response = readln().uppercase()
        if (response == "$trueChar") return 1 else if (response == "$falseChar") return -1 else if(response == "$nothingChar") return 0
        println("Introduce un valor valido.")
    }while (true)
}

fun choseDificultad(): Int {
    println("Que dificultad quieres facil, normal o dificil: ")
    do {
        val response = readln().uppercase()
        if (response == "FACIL") return 5
        if (response == "NORMAL") return 9
        if (response == "DIFICIL") return 14
        println("Introduce un valor valido 'facil, normal o dificil'.")
    }while (true)
}

private fun mostrarMapa(mapa: Array<IntArray>) {
    for (i in mapa.indices){
        for (j in mapa[i].indices){
            print(when(mapa[i][j]){
                0 -> "$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
                1 -> "$ANSI_YELLOW_BACKGROUND   $ANSI_RESET"
                2 -> {
                    val index = enemigoIndex(arrayOf(i,j))
                    if (Enemigos[index].vida > 0)
                        "$ANSI_GREEN_BACKGROUND ${Enemigos[index].vida} $ANSI_RESET"
                    else
                        "$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
                }
                in 5..7 -> "$ANSI_RED_BACKGROUND   $ANSI_RESET"
                in 8..10 -> "$ANSI_BLUE_BACKGROUND ${11-mapa[i][j]} $ANSI_RESET"
                else ->"$ANSI_BLACK_BACKGROUND   $ANSI_RESET"
            })
        }
        println()
    }
}

fun enemigoIndex(pos: Array<Int>): Int {
    for (i in Enemigos.indices){
        if (Enemigos[i].pos.contentEquals(pos)) return i
    }
    return -1
}

private fun generarMapa(mapa: Array<IntArray>) {
    clearEnemigosArea(mapa)

    mapa[12] = intArrayOf(0,	8,	8,	8,	8,	0,	0,	8,	8,	8,	8,	0,	0,	8,	8,	8,	8,	0,	0,	8,	8,	8,	8,	0)
    mapa[13] = intArrayOf(0,	8,	0,	0,	8,	0,	0,	8,	0,	0,	8,	0,	0,	8,	0,	0,	8,	0,	0, 8,	0,	0,	8,	0)

    clearPlayerArea(mapa)

    EnemigosMovimientoCount = 1

    //Player
    PlayerPos = intArrayOf(15,9)
    spawnPlayer(mapa)

    //Enemigos
    spawnEnemigos(mapa)
}

private fun clearEnemigosArea(mapa: Array<IntArray>) {
    for (i in 0..11) {
        mapa[i] = IntArray(mapa[i].size) { 0 }
    }

    mapa[12] = intArrayOf(0,	mapa[12][1],	mapa[12][2],	mapa[12][3],	mapa[12][4],	0,	0,	mapa[12][7],	mapa[12][8],	mapa[12][9],	mapa[12][10],	0,	0,	mapa[12][13],	mapa[12][14],	mapa[12][15],	mapa[12][16],	0,	0,	mapa[12][19],	mapa[12][20],	mapa[12][21],	mapa[12][22],	0)
    mapa[13] = intArrayOf(0,	mapa[13][1],	0,	0,	mapa[13][4],	0,	0,	mapa[13][7],	0,	0,	mapa[13][10],	0,	0,	mapa[13][13],	0,	0,	mapa[13][16],	0,	0, mapa[13][19],	0,	0,	mapa[13][22],	0)
}

fun spawnEnemigos(mapa: Array<IntArray>) {
    var index = 0
    for (i in 1..5){
        if (i % 2 != 0){
            for (j in 1..19){
                if (j % 2 != 0){
                    mapa[i][j] = 2
                    Enemigos[index].pos = intArrayOf(i,j).toTypedArray()

                    if (Dificultad >= 9){
                        Enemigos[index].vida = (2..3).random()
                    }else{
                        Enemigos[index].vida = (1..3).random()
                    }

                    index++
                }
            }
        }
    }
}

fun spawnPlayer(mapa: Array<IntArray>){
    clearPlayerArea(mapa)
    mapa[PlayerPos[0]][PlayerPos[1]] = 1 // Centro
    mapa[PlayerPos[0]-1][PlayerPos[1]] = 1 // Arriba
    mapa[PlayerPos[0]][PlayerPos[1]-1] = 1 // Izq
    mapa[PlayerPos[0]][PlayerPos[1]+1] = 1 // Der
}

private fun clearPlayerArea(mapa: Array<IntArray>) {
    for (i in 14..15) {
        mapa[i] = IntArray(mapa[i].size) { 0 }
    }
}

private class Enemigo(var pos: Array<Int>, var vida: Int)
private class Disparo(val pos: Array<Int>, val direccion: Int)