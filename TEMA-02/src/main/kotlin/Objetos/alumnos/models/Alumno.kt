package Objetos.alumnos.models

import java.time.LocalDateTime
import java.util.UUID

class Alumno {
    // region Estado
    private val id = UUID.randomUUID()
    private var nombre = ""
    private var apellido = ""
    private var calificacion = 0.0 //0.0-10.0
    private val createAt = LocalDateTime.now()

    // Kotlin   ->  private set
    // C#       ->  String nombre { get ; private set }
    // endregion

    // region Metodos
    constructor(nombre: String, apellido: String, calificacion: Double){
        println("Nuevo alumno!!!")

        setNombre(nombre)
        setApellido(apellido)
        setCalificacion(calificacion)
    }

    fun info(): String{
        return "Id:$id,Nombre:$nombre,Apellido:$apellido,Calificacion:$calificacion,CreateAt:$createAt"
    }

    fun saludar(){
        println("Hola soy $nombre $apellido y mi nota es $calificacion")
    }

    // region Get
    fun getId(): UUID = id
    fun getNombre(): String = nombre
    fun getApellido(): String = apellido
    fun getNombreCompleto(): String = "$nombre $apellido"
    fun getCalificacion(): Double = calificacion
    fun getCreateAt(): LocalDateTime = createAt
    // endregion

    // region Set
    fun setNombre(nombre: String) { this.nombre = nombre }
    fun setApellido(apellido: String) { this.apellido = apellido }
    fun setNombreCompleto(nombreCompleto: String) {
        val splitNombre = nombreCompleto.split(" ")
        if (splitNombre.size > 2) { return }
        this.nombre = splitNombre[0]
        this.apellido = splitNombre[1]
    }
    fun setCalificacion(calificacion:Double){
        val regex = Regex("^(\\d|10).(\\d|\\d{2})\$")
        if (!regex.matches(calificacion.toString())) { return }
        this.calificacion = calificacion
    }
    // endregion

    // endregion
}