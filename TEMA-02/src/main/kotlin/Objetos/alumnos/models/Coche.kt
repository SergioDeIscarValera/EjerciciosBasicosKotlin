package Objetos.alumnos.models

class Coche {
    // region Atributos
    var matricula = ""
        private set
    var marca = ""
        private set
    var anioFabricacion = 0
        //private set
    private var revision = false
    // endregion

    // region Metodos

    fun info(): String{
        return "La matricula es $matricula, su marca es $marca y fue fabricado en el $anioFabricacion (necesita revisión: $revision)"
    }

    fun generateRandomCar(){
        matricula = randomMatricula()
        marca = randomMarca()
        anioFabricacion = randomAnio()
        comprobarRevision()
    }

    private fun randomMatricula(): String {
        return when((0..3).random()){
            0 -> "5061 OEX"
            1 -> "6723 KYR"
            2 -> "9655 LZL"
            else -> "1473 XYP"
        }
    }

    private fun randomMarca(): String {
        return when((0..3).random()){
            0 -> "Ford"
            1 -> "Peugeot"
            2 -> "Subaru"
            else -> "Honda"
        }
    }

    private fun randomAnio(): Int{
        return (1980..2022).random()
    }

    fun comprobarRevision(){
        revision = anioFabricacion % 2 == 0
    }

    // endregion
}