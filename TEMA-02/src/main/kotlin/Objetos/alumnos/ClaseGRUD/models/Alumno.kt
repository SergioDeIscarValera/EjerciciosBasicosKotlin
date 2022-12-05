package Objetos.alumnos.ClaseGRUD.models

import kotlin.math.roundToInt

data class Alumno (
    val id: Int,
    val name: String,
    val qualification: Double
    ){
    val qualificationRound get() = (qualification * 100.0).roundToInt() / 100.0
    val isAprobado get() = qualification >= 5
    init {
        require(id >= 0) { "El id debe ser positivo" }
        require(name.isNotEmpty()) { "Se ha de indicar un nombre" }
        require(qualification in 0.0..10.0) { "La calificación debe estar entre 0 y 10" }
        println("Nuevo alumno!!")
    }
}