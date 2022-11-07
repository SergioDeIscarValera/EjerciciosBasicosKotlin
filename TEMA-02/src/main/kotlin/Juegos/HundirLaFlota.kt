private val MAPA = arrayOf(1, 5) // Tamaño del mapa, extremos incluidos
private val BARCOS_POS_IA = Array(4){ arrayOf(0,0)} // Almacena la posición de cada barco
private val BARCOS_POS_HUMANO = Array(4){ arrayOf(0,0)} // Almacena la posición de cada barco
private var HISTORY_HITS_HUMANO = mutableListOf<Hit>() // Historial de donde ya has disparado
private var HISTORY_HITS_IA = mutableListOf<Hit>() // Historial de donde ya ha disparado

fun main() {
    // region SPAWN
    val iaMemory = iaMemoria()

    for (i in BARCOS_POS_IA.indices){
        spawnBarcoIa(i)
    }

    println("Tienes que introducir 4 barcos dentro de los limites (${MAPA[0]}-${MAPA[1]})")

    for (i in BARCOS_POS_HUMANO.indices){
        spawnBarcoHumano(i)
    }
    // endregion

    // region TURNOS
    var barcosVivosIa =  4
    var barcosVivosHumano =  4
    var count = 1

    do {
        println("Tus barcos con vida $barcosVivosHumano y los barcos de la IA con vida $barcosVivosIa | turno $count")
        //Primero el Humano
        barcosVivosIa -= turnoHumano()

        if(barcosVivosIa == 0) { break } // Por si ya ha ganado

        //Luego la IA
        barcosVivosHumano -= turnoIa(iaMemory)

        println("Mapa de tu tablero:")
        printMapa(false)
        println()

        println("Mapa del tablero de la IA:")
        printMapa(true)
        println()

        count++
    }while (barcosVivosIa > 0 && barcosVivosHumano > 0)
    // endregion

    // region FINAL
    if (barcosVivosIa == 0){
        println("Has ganado!!!!")
    }else{
        println("Has perdido :(")
    }
    // endregion
}

/*
    Función para determinar si la IA va a recordar donde ha disparado.
 */
fun iaMemoria(): Boolean{
    println("Quieres que la IA tenga memoria de donde ha disparado (s/n):")
    do {
        val response = readln().lowercase()
        if(response == "s"){
            return true
        }
        if(response == "n"){
            return false
        }
    }while (true)
}

private fun response(): Array<Int>{
    val response = arrayOf(-1, -1)
    for(i in 0..1){
        var res: Int
        loop@do {
            res = readln().toIntOrNull() ?: -1
            if( res == -1 || res !in MAPA[0]..MAPA[1]){ println("Escribe un número valido")} else { break@loop }
        }while (true)
        response[i] = res
    }
    return response
}

fun spawnBarcoIa(index: Int){
    var newPos: Array<Int>
    do {
        var condition = false
        newPos = arrayOf((MAPA[0]..MAPA[1]).random(), (MAPA[0]..MAPA[1]).random()) // Nueva posición random

        loop@for (i in BARCOS_POS_IA){ // Comprobando que no sea repetida
            if (i.contentEquals(newPos)){
                condition = true
                break@loop
            }
        }

    }while (condition)

    BARCOS_POS_IA[index] = newPos
}

fun spawnBarcoHumano(index: Int){
    var newPos: Array<Int>
    do {
        var condition = false
        println("Introduce la posición de tu barco número ${index + 1}:")
        newPos = response()
        println(newPos.joinToString() + "\n")
        loop@for (i in BARCOS_POS_HUMANO){ // comprobando que no sea repetida
            if (i.contentEquals(newPos)){
                condition = true
                println("Esa posición ya la has introducido")
                break@loop
            }
        }
    }while (condition)

    BARCOS_POS_HUMANO[index] = newPos
}

fun turnoIa(memory: Boolean): Int{
    println("Turno de la IA:")
    //Generar donde va ha hacer el hit
    var posHit: Array<Int>
    do {
        var condition = false
        posHit = arrayOf((MAPA[0]..MAPA[1]).random(), (MAPA[0]..MAPA[1]).random())

        if(memory){
            repetido@for (i in HISTORY_HITS_IA){
                if(i.pos.contentEquals(posHit)){
                    condition = true
                    break@repetido
                }
            }
        }

    }while (condition)

    for(i in BARCOS_POS_HUMANO.indices){
        if(BARCOS_POS_HUMANO[i].contentEquals(posHit)){
            BARCOS_POS_HUMANO[i] = arrayOf(-1,-1) // para que no pueda ser reundido
            println("Hit te ha dado \n")
            HISTORY_HITS_IA.add(Hit(posHit, true))
            return 1
        }
    }
    println("No te ha dado \n")
    HISTORY_HITS_IA.add(Hit(posHit, false))
    return 0
}

fun turnoHumano(): Int{
    println("Tu turno:")

    var posHit: Array<Int>
    do {
        println("Introduce la posición donde vas a disparar:")

        var condition = false

        posHit = response()
        println(posHit.joinToString())

        repetido@for (i in HISTORY_HITS_HUMANO){
            if(i.pos.contentEquals(posHit)){
                condition = true
                println("Ya has introducido esta posición")
                break@repetido
            }
        }
    }while (condition)

    for(i in BARCOS_POS_IA.indices){
        if(BARCOS_POS_IA[i].contentEquals(posHit)){
            BARCOS_POS_IA[i] = arrayOf(-1,-1) // para que no pueda ser rehundido
            println("Hit le has dado \n")
            HISTORY_HITS_HUMANO.add(Hit(posHit, true))
            return 1
        }
    }
    println("No le has dado \n")
    HISTORY_HITS_HUMANO.add(Hit(posHit, false))
    return 0
}

/*
    Función para mostrar el mapa, marcando donde ya hemos disparado y donde hemos destruido un barco
    - -> nada
    o -> ya disparado
    x -> Hundido
 */
fun printMapa(humanOrIa: Boolean){
    val history = if(humanOrIa){ HISTORY_HITS_HUMANO } else { HISTORY_HITS_IA }
    val matriz = mutableListOf(arrayOf(""))
    for (i in MAPA[0]..MAPA[1]){
        val row: MutableList<String> = arrayListOf()
        for (j in MAPA[0]..MAPA[1]){
            var id = 0
            //Se comprueba si ya se ha disparado
            //Se comprueba si la posición ha sido undido un barco
            for (ii in history){
                if(ii.pos[0] == j && ii.pos[1] == i){
                    id = if (ii.doDamage){
                        2
                    }else{
                        1
                    }
                }
            }

            when(id){
                0 -> row.add("-")
                1 -> row.add("O")
                2 -> row.add("X")
            }
        }
        matriz.add(row.toTypedArray())
    }

    for (i in matriz){
        for (j in i){
            print("$j\t")
        }
        println()
    }
}

private class Hit(val pos: Array<Int>, var doDamage: Boolean) // :( es solo una pequeña clase