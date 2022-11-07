package Examen

fun main(){
    var macAddress = Array(0){""}
    do {
        when(menu()){
            0 -> mostrarMacAddress(macAddress)
            1 -> { macAddress = addAddress(macAddress, responseAddress("Introduce la nueva Mac:")); mostrarMacAddress(macAddress) }
            2 -> { macAddress = updateAddress(macAddress, responseAddress("Introduce la Mac que quieres editar:"), responseAddress("Introduce la nueva Mac:")); mostrarMacAddress(macAddress) }
            3 -> { macAddress = deleteAddress(macAddress, responseAddress("Introduce la Mac que quieres eliminar")); mostrarMacAddress(macAddress) }
            4 -> println("El estado de la MAC: ${findAddressBoolean(macAddress, responseAddress("Introduce la MAC a buscar:"))}")
        }
    }while (true)
}

fun responseAddress(mensaje: String): String {
    println(mensaje)
    var response: String
    val regex = Regex("([0-9a-fA-F]{2}(:|-)){5}[0-9a-fA-F]{2}")
    do {
        response = readln()
        if (!regex.matches(response)){ println("MAC erronea") } else { break }
    }while (true)
    return response
}

fun menu(): Int {
    println("¿Qué acción quieres realizar? | mostrar | añadir | editar | eliminar | buscar |")
    do{
        when(readln().lowercase()){
            "mostrar" -> return 0
            "añadir" -> return 1
            "editar" -> return 2
            "eliminar" -> return 3
            "buscar" -> return 4
            else -> println("| mostrar | añadir | editar | eliminar | buscar |")
        }
    }while (true)
}

fun cloneArray(input:Array<String>, increment: Int?): Array<String>{
    val newArray: Array<String> = if (increment != null) Array(input.size+increment){""}
    else Array(input.size){""}
    for (i in input.indices) newArray[i] = input[i]
    return newArray
}

fun addAddress(macAddress: Array<String>, address:String): Array<String>{
    val newArray = cloneArray(macAddress,1)
    newArray[newArray.indices.last] = address
    return newArray
}

fun findAddressBoolean(macAddress: Array<String>, address:String): Boolean{
    for (i in macAddress){
        if (i == address){
            return true
        }
    }
    return false
}

fun findAddressIndex(macAddress: Array<String>, address:String): Int{
    for (i in macAddress.indices){
        if (macAddress[i] == address){
            return i
        }
    }
    println("No encontrado")
    return -1
}

fun updateAddress(macAddress: Array<String>, addressOld: String, addressNew: String): Array<String>{
    var macAddress = macAddress
    val index =  findAddressIndex(macAddress, addressOld)
    if (index == -1){ return macAddress }
    macAddress = updateAddress(macAddress, index, addressNew)
    return macAddress
}

fun updateAddress(macAddress: Array<String>, index:Int, addressNew: String): Array<String>{
    macAddress[index] = addressNew
    return macAddress
}

fun deleteAddress(macAddress: Array<String>, address:String): Array<String>{
    val index = findAddressIndex(macAddress, address)
    if (index == -1){ return macAddress }
    return deleteAddress(macAddress, index)
}

fun deleteAddress(macAddress: Array<String>, index:Int): Array<String>{
    val newArray = Array(macAddress.size - 1){""}
    var indexToWrite = 0
    for (i in macAddress.indices){
        if (i != index){
            newArray[indexToWrite] = macAddress[i]
            indexToWrite++
        }
    }
    return newArray
}

fun mostrarMacAddress(macAddress: Array<String>){
    for (i in macAddress.indices){
        println("Mac ${i + 1}: ${macAddress[i]}")
    }
}