package ExpRegulares

fun main(){
    val text = "Texto de prueba"
    val textSplited = splitManual(text, ' ')
    for (i in textSplited){
        println(i)
    }
}

fun splitManual(text: String, delimitador: Char): Array<String>{
    val array = Array<String>(countChar(text, delimitador)){""}
    var lastIndex = 0
    var count = 0
    for (i in text.indices){
        if (text[i] == delimitador)
        {
            for (j in lastIndex until i){
                array[count] += text[j].toString()
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
    return count +1
}