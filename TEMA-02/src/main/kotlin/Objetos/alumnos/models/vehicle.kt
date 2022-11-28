package Objetos.alumnos.models

import java.time.LocalDateTime

class Vehicle {
    // region Estado
    private var matricula = ""
    private var marca = ""
    private var modelo = ""
    private var motor = "" // diesel | gasolina | hibrido | electrico
    private var dateCreation = 0 // YYYY
    // endregion

    // region Metodos
    constructor(randomGenerate:Boolean){
        if (randomGenerate){
            setMatricula(randomMatricula())
            setMarca(randomMarca())
            setModelo(randomModelo())
            setMotor(randomMotor())
            setDateCreate(randomDateCration())
        }
        println("Vehículo construido")
    }
    init {
        println("Vehículo Iniciado")
    }

    fun info(): String{
        return "Matricula:${getMatricula()}\tMarca:${getMarca()}\tModelo:${getModelo()}\tMotor:${getMotor()}\tDateCration:${getDateCreation()}\tContaminante:${isPollutant()}\tAntiguo:${isOld()}"
    }

    fun isPollutant(): Boolean{
        return getMotor() == "Diesel" || getMotor() == "Gasolina"
    }

    fun isOld():Boolean{
        return (LocalDateTime.now().year - getDateCreation() ) > 10
    }

    override fun equals(other: Any?): Boolean{
        return if(other is Vehicle){
            this.matricula == other.matricula &&
                this.marca == other.marca &&
                this.modelo == other.modelo &&
                this.motor == other.motor &&
                this.dateCreation == other.dateCreation
        } else false
    }

    override fun toString(): String{
        return this.info()
    }

    override fun hashCode(): Int {
        var result = matricula.hashCode()
        result = 31 * result + marca.hashCode()
        result = 31 * result + modelo.hashCode()
        result = 31 * result + motor.hashCode()
        result = 31 * result + dateCreation.hashCode()
        return result
    }

    // region Random functions
    fun randomMatricula(): String {
        return when((0..3).random()){
            0 -> "5061 OEX"
            1 -> "6723 KYR"
            2 -> "9655 LZL"
            else -> "1473 XYP"
        }
    }

    fun randomMarca(): String {
        return when((0..3).random()){
            0 -> "Ford"
            1 -> "Peugeot"
            2 -> "Subaru"
            else -> "Honda"
        }
    }

    fun randomModelo(): String {
        return when((0..3).random()){
            0 -> "Minicompacto"
            1 -> "Subcompacto"
            2 -> "Familiar"
            else -> "Monovolúmenes"
        }
    }

    fun randomMotor(): String {
        return when((0..3).random()){
            0 -> "Diesel"
            1 -> "Gasolina"
            2 -> "Híbrido"
            else -> "Eléctrico"
        }
    }

    fun randomDateCration(): Int{
        return (1980..2022).random()
    }
    // endregion

    // region Get
    fun getMatricula(): String = matricula
    fun getMarca(): String = marca
    fun getModelo(): String = modelo
    fun getMotor(): String = motor
    fun getDateCreation(): Int = dateCreation
    // endregion

    // region Set
    fun setMatricula(matricula: String){
        val regex = Regex("^\\d{4} [A-Z]{3}$")
        if (!regex.matches(matricula)) { return }
        this.matricula = matricula
    }
    fun setMarca(marca:String) {
        if ( marca != "Ford" && marca != "Peugeot" && marca != "Subaru" && marca != "Honda" ) { return }
        this.marca = marca
    }
    fun setModelo(modelo:String) {
        if ( modelo != "Minicompacto" && modelo != "Subcompacto" && modelo != "Familiar" && modelo != "Monovolúmenes" ) { return }
        this.modelo = modelo
    }
    fun setMotor(motor:String) {
        if ( motor != "Diesel" && motor != "Gasolina" && motor != "Híbrido" && motor != "Eléctrico" ) { return }
        this.motor = motor
    }
    fun setDateCreate(dateCreation:Int) {
        val regex = Regex("^\\d{4}$")
        if (!regex.matches(dateCreation.toString())) { return }
        this.dateCreation = dateCreation
    }
    // endregion
    // endregion
}