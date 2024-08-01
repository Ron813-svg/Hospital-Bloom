package Modelos

import java.sql.DriverManager

class Conexion {

        fun cadenaConexion(): java.sql.Connection? {
            try {
                val ip = "jdbc:oracle:thin:@192.168.0.7:1521:xe"
                val usuario = "system"
                val contrasena = "ITR2024"

                val conexion = DriverManager.getConnection(ip, usuario, contrasena)
                return conexion
            }
            catch (e: Exception){
                println("Este es el error: $e")
                return null
            }
        }
}