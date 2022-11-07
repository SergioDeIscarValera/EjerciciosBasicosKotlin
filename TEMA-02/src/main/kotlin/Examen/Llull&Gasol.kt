fun main(){
    var puntuacionLlull = 0
    var puntuacionGasol = 0
    
    for (i in 0..4){
        if (tiro(50)){
            if(i < 4){
                puntuacionLlull++
            }else{
                puntuacionLlull += 2
            }
        }
        if (tiro(33)){
            if(i < 4){
                puntuacionGasol++
            }else{
                puntuacionGasol += 2
            }
        }
    }
    println(puntuacionLlull)
    println(puntuacionGasol)
}

fun tiro(procentaje:Int):Boolean{
    return ((0..100).random() in 0..procentaje)
}