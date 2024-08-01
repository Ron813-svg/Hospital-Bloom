package Modelos

data class tbPacientes(
    val uuid: String,
    val nombrePaciente: String,
    val tipoSangrePaciente: String,
    val telefonoPaciente: String,
    val enfermedadPaciente: String,
    val numHabitacionPaciente: String,
    val numCamaPaciente: String,
    val medicamentosPaciente: String,
    val fechaNacimientoPaciente: String,
    val horaAplicacionPaciente: String
)
