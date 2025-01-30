package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conection {

    public Conection(){
        initConection();
    }

    private void initConection() {
        String url = "jdbc:postgresql://localhost:5432/Preuniversitario";
        String usuario = "postgres";
        String contraseña = "SeVeRnAsTeR";

        // Objeto para la conexión
        Connection conexion = null;

        try {
            // Registrar el driver de PostgreSQL (no es necesario en versiones recientes de
            // JDBC)
            Class.forName("org.postgresql.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(url, usuario, contraseña);

            // Verificar si la conexión fue exitosa
            if (conexion != null) {
                System.out.println("¡Conexión exitosa!");
            } else {
                System.out.println("¡Conexión fallida!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de PostgreSQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }

        
    }

    public static void main(String[] args) {
        new Conection();
    }

}
