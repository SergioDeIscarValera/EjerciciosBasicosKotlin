fun main(){
    val SIZE = 10
    val MIN = -20
    val MAX = 20

    val panel = Array(SIZE){ IntArray(SIZE){ (MIN..MAX).random() } }

    for (i in panel){
        for (j in i){
            print("$j\t")
        }
        println()
    }
    println()

    val pos = introducirPosicion(panel)

    val posIz = clone(pos.toIntArray())
    println("Suma a la izquierda: ${sumaLado(panel, posIz.toTypedArray(), -1)}")

    val posDer = clone(pos.toIntArray())
    println("Suma a la derecha: ${sumaLado(panel, posDer.toTypedArray(), 1)}")

    val posIzMayor = clone(pos.toIntArray())
    println("Mayor a la izquierda: ${mayorLado(panel, posIzMayor.toTypedArray(), -1)}")
    val posIzMenor = clone(pos.toIntArray())
    println("Menor a la izquierda: ${menorLado(panel, posIzMenor.toTypedArray(), -1)}")

    val posDerMayor = clone(pos.toIntArray())
    println("Mayor a la derecha: ${mayorLado(panel, posDerMayor.toTypedArray(), 1)}")
    val posDerMenor = clone(pos.toIntArray())
    println("Menor a la derecha: ${menorLado(panel, posDerMenor.toTypedArray(), 1)}")
}

fun introducirPosicion(panel: Array<IntArray>): Array<Int> {
    val pos = IntArray(2) {-1}

    var resROW: Int
    println("Introduce la fila:")
    do {
        resROW = readln().toIntOrNull() ?: -1
    }while (resROW !in 1..panel.size)
    pos[0] = resROW - 1

    var resCOLUM: Int
    println("Introduce la columna:")
    do {
        resCOLUM = readln().toIntOrNull() ?: -1
    }while (resCOLUM !in 1..panel.size)
    pos[1] = resCOLUM - 1

    return pos.toTypedArray()
}

fun sumaLado (panel: Array<IntArray>, pos: Array<Int>, direccion: Int): Int{
    var count = 0
    pos[1] += direccion
    while (pos[0] in panel.indices && pos[1] in panel.indices){
        count += panel[pos[0]][pos[1]]
        pos[1] += direccion
    }
    return count
}

fun mayorLado (panel: Array<IntArray>, pos: Array<Int>, direccion: Int): Int{
    val initial = panel[pos[0]][pos[1]]
    var count = 0
    pos[1] += direccion
    while (pos[0] in panel.indices && pos[1] in panel.indices){
        if (panel[pos[0]][pos[1]] > initial) count++
        pos[1] += direccion
    }
    return count
}

fun menorLado (panel: Array<IntArray>, pos: Array<Int>, direccion: Int): Int{
    val initial = panel[pos[0]][pos[1]]
    var count = 0
    pos[1] += direccion
    while (pos[0] in panel.indices && pos[1] in panel.indices){
        if (panel[pos[0]][pos[1]] < initial) count++
        pos[1] += direccion
    }
    return count
}

fun clone(array: IntArray): IntArray {
    val arrayClone = IntArray(array.size)
    for (i in array.indices){
        arrayClone[i] = array[i]
    }
    return arrayClone
}