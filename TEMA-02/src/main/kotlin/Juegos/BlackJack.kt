private val BARAJA = Array(52){ Carta("",0) }
private val CARTAS_IA = mutableListOf<Carta>()
private val CARTAS_HUMANO = mutableListOf<Carta>()

fun main(){
    var saldo = 500

    println("Bienvenido al Balck Jack")
    do {
        generateBaraja()
        CARTAS_HUMANO.clear()
        CARTAS_IA.clear()
        BARAJA.shuffle() // :( es solo una funcioncita

        println("\n ¿Cuanto quieres apostar?: (saldo actual: $saldo)")
        val apusta = apustaFn(saldo)
        saldo -= apusta
        println("Has apostado $apusta y ahora tu saldo es de $saldo \n")

        do {
            cartaParaHumano()
            cartaParaIa()
        }while (!plantarse() && valorManoIA() <= 16)

        println("Tu mano tiene un valor de ${valorManoHumano()}")
        println("La mano de la IA tiene un valor de ${valorManoIA()}")


        val diferenHumano = (21 - valorManoHumano())
        val diferenIa = (21 - valorManoIA())

        if(diferenIa < 0){
            if (diferenHumano < 0){
                println("Te has pasado, empate")
                saldo += apusta
            }else{
                println("La Ia se ha pasado")
                saldo += apusta*2
            }
        }
        else if (diferenHumano < 0){
            println("Te has pasado, pierdes")
        }
        else if (diferenHumano == diferenIa){
            println("Empate")
            saldo += apusta
        }
        else if(diferenHumano < diferenIa ){
            println("Has ganado")
            saldo += apusta*2
        }else{
            println("Has perdido")
        }

    }while (saldo >= 50 && saldo < 5000)
}

fun apustaFn(saldo: Int): Int {
    var response: Int
    do {
        response = readln().toIntOrNull() ?: -1
        if (response !in 50..saldo){ println("Escribe un número valido (entre 50 y tu saldo)") }
    }while (response !in 50..saldo)
    return response
}

fun generateBaraja(){
    var index = 0
    for(i in 0..3){
        when(i){
            0 -> { // Tréboles
                generateTipo("trébol", index)
                index += 13
            }
            1 -> { // Diamantes
                generateTipo("diamante", index)
                index += 13
            }
            2 -> { // Corazones
                generateTipo("corazón", index)
                index += 13
            }
            3 -> { // Picas
                generateTipo("pica", index)
            }
        }
    }
}

fun generateTipo(tipo: String, index: Int){
    for (i in index..index + 12){
        BARAJA[i] = Carta(tipo, (i - index) + 1)
    }
}

fun cartaParaHumano() {
    for (i in BARAJA){
        if(i.numero != -1){
            CARTAS_HUMANO.add(Carta(i.tipo,i.numero))
            i.numero = -1
            break
        }
    }
}

fun cartaParaIa() {
    for (i in BARAJA){
        if(i.numero != -1){
            CARTAS_IA.add(Carta(i.tipo,i.numero))
            i.numero = -1
            break
        }
    }
}

fun plantarse(): Boolean {
    println("Estas son tus cartas:")
    for (i in CARTAS_HUMANO){
        println("${i.numero} de ${i.tipo}")
    }
    println("Con un valor total de ${valorManoHumano()}")
    println("\n ¿Quieres plantarte? (s/n)")
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

fun valorManoHumano(): Int{
    var suma = 0

    var countAS = 0
    for (i in CARTAS_HUMANO){
        when(i.numero){
            1 -> countAS++
            in 2..10 -> suma += i.numero
            in 11..13 -> suma += 10
        }
    }

    if(countAS > 0){
        for (i in 0 until  countAS){
            if((suma + (11 * countAS)) > 21){
                suma++
            }else{
                suma += 11
            }
        }
    }

    return suma
}

fun valorManoIA(): Int{
    var suma = 0

    var countAS = 0
    for (i in CARTAS_IA){
        when(i.numero){
            1 -> countAS++
            in 2..10 -> suma += i.numero
            in 11..13 -> suma += 10
        }
    }

    if(countAS > 0) {
        for (i in 0..countAS) {
            if (suma + (11 * countAS) > 21) {
                suma++
            } else {
                suma += 11
            }
        }
    }

    return suma
}

private class Carta(val tipo: String, var numero: Int) // :( es solo una pequeña clase