package ExpRegulares

fun main(){
    var text = "  Texto de prueba  "
    text = trimManual(text)
    val textSplited = splitManual(text, ' ')
    for (i in textSplited){
        println(i)
    }
    println(contentToStringManual(textSplited))
    println(joinToStringManual(textSplited))
    println(joinToStringManual(reverseManual(textSplited)))
    println(containsManual(textSplited, "Texto"))
}

fun containsManual(array: Array<String>, toFind: String): Boolean {
    for (i in array){
        if (i.length == toFind.length){
            var check = true
            loopPalabra@for (j in i.indices){
                if (i[j] != toFind[j]){
                    check = false
                    break@loopPalabra
                }
            }
            if (check) return check
        }
    }
    return false
}

fun reverseManual(array: Array<String>): Array<String> {
    val newArray = Array(array.size){ "" }
    for (i in array.lastIndex downTo 0){
        newArray[i] = array[i]
    }
    return newArray
}

fun contentToStringManual(array: Array<String>): String {
    var arrayContent = "{ "
    arrayContent += joinToStringManual(array)
    arrayContent += "}"
    return arrayContent
}

fun joinToStringManual(array: Array<String>):String{
    var arrayContent = ""
    for (i in array){
        if (i == array.last())  arrayContent += "$i "
        else arrayContent += "$i, "
    }
    return arrayContent
}

fun trimManual(text: String): String {
    var initialIndex = 0
    var lastIndex = 0

    for (i in text.indices){
        if (text[i] != ' '){
            initialIndex = i
            break
        }
    }
    for (i in text.lastIndex downTo 0){
        if (text[i] != ' '){
            lastIndex = i
            break
        }
    }

    var newText = ""
    for (i in initialIndex..lastIndex){
        newText += text[i]
    }
    return newText
}

fun splitManual(text: String, delimitador: Char): Array<String>{
    val array = Array<String>(countChar(text, delimitador) + 1){""}
    var lastIndex = 0
    var count = 0
    for (i in text.indices){
        if (text[i] == delimitador || i == text.lastIndex)
        {
            for (j in lastIndex.. i){
                array[count] += text[j].toString()
                array[count] = trimManual(array[count])
            }
            count++
            lastIndex = i+1
        }
    }
    return array
}

fun countChar(text: String, char: Char): Int{
    var count = 0
    for (i in text){
        if (i == char) count++
    }
    return count
}