fun main(){
    val mapa = generateMapa()
}

private fun generateMapa(): Array<Int> {
    var array: Array<Int>
    do {
        println("Introduce el tamaño del tablero, excribiendo el minimo y el maximo")
        array = arrayOf(returnNum(),returnNum())

        if(array[0] % 2 == 0 && array[1] % 2 == 0 && array[0] > array[1]){ println("Tablero invalido") }
    }while (array[0] % 2 == 0 && array[1] % 2 == 0 && array[0] > array[1])
    return array
}

private fun returnNum():Int{
    var response: Int
    do {
        response = readln().toIntOrNull() ?: -1
        if( response == -1){ println("Escribe un número valido") }
    }while (response == -1)
    println("El número $response ha sido introducido \n")
    return response
}
