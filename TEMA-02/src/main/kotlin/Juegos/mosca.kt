private val MAP = arrayOf(0,9)
private const val AREA_HIT = 1 // Si la mosca esta ha este número de casillas saldrá volando y cambiará la posición
fun main() {
    println("¿Dónde está la mosca?")
    println("Escribe la posicion entre ${MAP[0]} y ${MAP[1]} (x,y):")
    var flyPos: Array<Int> = spawnFly()
    val responsePos = IntArray(2)
    do {
        println("FlyPos: ${flyPos.joinToString()}")
        responsePos[0] = returnNum() // x de tu Hit
        responsePos[1] = returnNum() // y de tu Hit

        if(flyPos[0] == responsePos[0] && flyPos[1] == responsePos[1] ){
            println("HIT WIN!!!!")
            return
        }
        if(flyPos[0] in (responsePos[0] - AREA_HIT)..(responsePos[0] + AREA_HIT) && flyPos[1] in (responsePos[1] - AREA_HIT)..(responsePos[1] + AREA_HIT) ){
            println("Near, ahora ha cambiado de posición")
            flyPos = spawnFly()
        }else{
            println("No le has dado prueba otra vez")
        }
    }while (true)
}

fun spawnFly(): Array<Int> {
    return arrayOf((MAP[0]..MAP[1]).random(), (MAP[0]..MAP[1]).random())
}

private fun returnNum():Int{
    var response = -1
    do {
        response = readln().toIntOrNull() ?: -1
        if( response == -1 || response !in MAP[0]..MAP[1]){ println("Escribe un número valido") }
    }while (response == -1 || response !in MAP[0]..MAP[1])
    println("El número $response ha sido introducido \n")
    return response
}