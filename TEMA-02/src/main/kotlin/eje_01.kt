fun main() {
    val bigArray = IntArray(10_000){ (0..100000).random() }

    val clonArray = bigArray.clone()
    var timeIni = 0L
    var timeFin = 0L

    timeIni = System.currentTimeMillis()
    //seleccionArray(clonArray)
    clonArray.sort()
    timeFin = System.currentTimeMillis()
    println("Tiempo: ${timeFin - timeIni}")

    val randomMatriz = generateRandomMatriz(50_000, 1_000)
    //mostrarMatriz(randomMatriz)

    timeIni = System.currentTimeMillis()
    ordenarMatriz(randomMatriz)
    timeFin = System.currentTimeMillis()
    println("Tiempo: ${timeFin - timeIni} \n")

    //mostrarMatriz(randomMatriz)


    //region call funtions
    /*
    val arryNumeros:Array<Short> = arrayOf(0,0,0,0,0)

    for (i in 0 until arryNumeros.size){
        arryNumeros[i] = introducirNumero()
        println()
    }

    println(arryNumeros.joinToString())
    println("El maximo es: " + maxArray(arryNumeros))
    println("El minimo es: " + minArray(arryNumeros))
    //println("El minimoIndex es: " + minArrayIndex(arryNumeros))
    println("La media es: " + mediaArray(arryNumeros))
    //println("Ordenado (burbuja): " + burbujaArray(arryNumeros).joinToString())
    //println("Ordenado (seleccion): " + seleccionArray(arryNumeros).joinToString())
    println("Invertir: " + invertArray(arryNumeros).joinToString())
    println("Tiene un 7: " + buscarArray(7, arryNumeros))
    println("Primitiva: " + generatePrimitive().joinToString())
     */
    //endregion
}

fun ordenarMatriz(randomMatriz: Array<IntArray>) {
    for (i in randomMatriz) {
        quickSort(i)
        //burbujaArray(i)
    }
    for (i in 1 until  randomMatriz.size) {
        for (j in 0 until randomMatriz.size - i) {
            if (randomMatriz[j][0] > randomMatriz[j + 1][0]) {
                val tem = randomMatriz[j + 1]
                randomMatriz[j + 1] = randomMatriz[j]
                randomMatriz[j] = tem
            }
        }
    }
}

private fun mostrarMatriz(matriz: Array<IntArray>) {
    for (i in matriz) {
        for (j in i) {
            print("$j\t")
        }
        println()
    }
}

fun generateRandomMatriz(sizeX:Int, sizeY:Int): Array<IntArray> {
    val matriz = Array(sizeY){IntArray(sizeX)}
    var value = 0
    for (i in 0 until matriz.size){
        val row = IntArray(sizeX)
        for (j in 0 until row.size){
            row[j] = value
            value++
        }
        row.shuffle()
        row.shuffle()
        matriz[i] = row
    }
    matriz.shuffle()
    matriz.shuffle()
    return matriz
}

fun introducirNumero(): Short{
    println("Introduce un número")
    var response = Short.MIN_VALUE
    do {
        response = readln().toShortOrNull() ?: Short.MIN_VALUE
    }while (response == Short.MIN_VALUE)
    return response
}

fun maxArray(arry: Array<Short>): Short{
    var max = Short.MIN_VALUE
    for(i in arry){
        if(max < i){ max = i }
    }
    return max
}

fun minArray(arry: Array<Short>): Short{
    var max = Short.MAX_VALUE
    for(i in arry){
        if(max > i){ max = i }
    }
    return max
}

fun minArrayIndex(arry: Array<Short>): Int{
    var min = Short.MAX_VALUE
    val index = 0
    for(index in 0 until arry.size){
        if(min > arry[index]){ min = arry[index] }
    }
    return index
}

fun mediaArray(arry: Array<Short>): Float{
    var media= 0.0f
    for(i in arry){
        media += i
    }
    media /= arry.size
    return media
}

fun burbujaArray(arry: IntArray){
    for (ii in 1 until  arry.size) {
        for (i in 0 until arry.size - ii) {
            if (arry[i] > arry[i + 1]) {
                val tem = arry[i + 1]
                arry[i + 1] = arry[i]
                arry[i] = tem
            }
        }
    }
}

fun seleccionArray(arry: IntArray){
    for(i in 0..(arry.size-2)){
        var index = i
        for( ii in i+1 until arry.size){
            if(arry[i] > arry[ii]){
                index = ii
            }
        }
        if(index != i){
            val tem = arry[i]
            arry[i] = arry[index]
            arry[index] = tem
        }
    }
}

fun buscarArray(num:Short, arry: Array<Short>):Boolean{
    for (i in 0 until arry.size){
        if(arry[i] == num){ return true }
    }
    return false
}

//Busqueda binaria, tiene que estar ordenado, se parte por la mitad si el que busca es menor coges la mitad inferior y si no coges la mitad superior...

fun invertArray(array: Array<Short>): ShortArray{
    val invertArray = ShortArray(array.size)
    for (i in array.size - 1 downTo  0){
        invertArray[(array.size - 1) - i] = array[i]
    }
    return invertArray
}

fun generatePrimitive(): ShortArray{
    val primitive = ShortArray(8)

    for (i in 0 until primitive.size){
        var random: Int

        do {
            var condicion = false
            random = (0..49).random()
            loop@for (ii in 0 until primitive.size){
                if(primitive[ii] == random.toShort()){ condicion = true; break@loop }
            }
        }while (condicion)

        primitive[i] = random.toShort()
    }

    return primitive
}

fun pivot(array: IntArray, left: Int, right: Int): Int {
    var i = left
    var j = right
    var pivot = array[left]
    while (i < j) {
        while (array[i] <= pivot && i < j) {
            i++
        }
        while (array[j] > pivot) {
            j--
        }
        if (i < j) {
            val aux = array[i]
            array[i] = array[j]
            array[j] = aux
        }
    }
    array[left] = array[j]
    array[j] = pivot
    return j
}

fun quicksort(array: IntArray, left: Int, right: Int) {
    val piv: Int
    if (left < right) {
        piv = pivot(array, left, right)
        quicksort(array, left, piv - 1)
        quicksort(array, piv + 1, right)
    }
}

fun quickSort(array: IntArray): Long {
    var timeIni = 0L
    var timeFin = 0L

    timeIni = System.currentTimeMillis()
    quicksort(array, 0, array.size - 1)

    timeFin = System.currentTimeMillis()
    return (timeFin - timeIni)
}