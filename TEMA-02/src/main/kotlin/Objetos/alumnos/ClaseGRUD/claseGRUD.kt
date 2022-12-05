package Objetos.alumnos.ClaseGRUD

import Objetos.alumnos.ClaseGRUD.models.Alumno

val CLASE = Array<Alumno?>(30){ null }
var lastId = -1

fun main(){
    postAlumno()
    val paco = getAlumno(0)
    println(paco)
}

fun getAlumnos(): Array<Alumno?>{
    val newArray = CLASE.clone()

    return newArray
}

fun getAlumno(id: Int): String{
    if (!exitsAlumno(id)){ println("Este alumno no existe"); return "" }
    for (i in CLASE){
        if (i != null && i.id == id){
            return """ "Alumno":{ "id":${i.id},"nombre":"${i.name}","calificación":${i.qualification} } """
        }
    }
    return ""
}

fun putAlumno(id: Int){
    if (!exitsAlumno(id)){ println("Este alumno no existe"); return }

}

fun deleteAlumno(id: Int){
    if (!exitsAlumno(id)){ println("Este alumno no existe"); return }
    for (i in CLASE.indices){
        if (CLASE[i] != null && CLASE[i]!!.id == id){
            CLASE[i] = null
        }
    }
}

fun postAlumno(){
    for (i in CLASE.indices){
        if (CLASE[i] == null){
            lastId += 1
            CLASE[i] = Alumno(lastId, inputName(), inputQualification())
            return
        }
    }
    println("La clase esta llena")
}


fun inputName(): String{
    var nombre:String
    do {
        println("Introduce el nombre del alumno:")
        nombre = readln()
    }while (nombre.isEmpty())
    return nombre.trim()
}

fun inputQualification(): Double{
    var qualification:Double
    do {
        println("Introduce la calificación del alumno:")
        qualification = readln().toDoubleOrNull() ?: -1.0
    }while (qualification !in 0.0..10.0)
    return qualification
}

fun exitsAlumno(id: Int):Boolean{
    for (i in CLASE){
        if (i != null && i.id == id){
            return true
        }
    }
    return false
}