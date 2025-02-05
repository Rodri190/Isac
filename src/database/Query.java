package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import database.modeloTablas.Persona;

public class Query {
    private Conection connection;

    public Query() {
        connection = new Conection();
    }

    public void insertarPersona(String nombre, String apellido, String ci, String celular, String email, String tipoPersona, String carrera, LocalDate fechaActual) {
        java.sql.Date fechaSql = java.sql.Date.valueOf(fechaActual);
        String insertarPersonaSQL = "insert into persona(nombre, apellido, ci, celular, email, tipo_persona, estado, fecha_registro) values(?,?,?,?,?,?,'Activo',?)";
        String insertarDatosExtra = "";
        if (tipoPersona.equals("Estudiante")) {
            insertarDatosExtra = "INSERT INTO estudiante (id_persona, carrera) VALUES (LAST_INSERT_ID(), ?)";
        } else {
            insertarDatosExtra = "INSERT INTO docente (id_persona) VALUES (LAST_INSERT_ID())";
        }

        // Usamos try-with-resources para manejar la conexión y declaraciones
        // automáticamente
        try (Connection conn = connection.getConnection()) {
            // Iniciar la transacción
            conn.setAutoCommit(false);

            // Insertar en la tabla Personas
            try (PreparedStatement pstmtPersona = conn.prepareStatement(insertarPersonaSQL)) {
                pstmtPersona.setString(1, nombre);
                pstmtPersona.setString(2, apellido);
                pstmtPersona.setString(3, ci);
                pstmtPersona.setString(4, celular);
                pstmtPersona.setString(5, email);
                pstmtPersona.setString(6, tipoPersona);
                pstmtPersona.setDate(7, fechaSql);
                pstmtPersona.executeUpdate();
            }

            // Insertar en la tabla Estudiantes
            try (PreparedStatement pstmtEstudiante = conn.prepareStatement(insertarDatosExtra)) {
                if (tipoPersona.equals("Estudiante")) {
                    pstmtEstudiante.setString(1, carrera);
                }
                pstmtEstudiante.executeUpdate();
            }

            // Confirmar la transacción
            conn.commit();
            System.out.println(tipoPersona + " insertado correctamente.");
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            e.printStackTrace();
        }
    }

    public ArrayList<Persona> selectTodasLasPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        String selectPersonasSQL = "SELECT * FROM persona";
        Date fechaSQL;
        String fechaFormateada;

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectPersonasSQL)) {

            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {
                fechaSQL = res.getDate("fecha_registro");
                // Convertir a java.util.Date
                java.util.Date fechaUtil = new java.util.Date(fechaSQL.getTime());

                // Formatear a "dd-MM-yy"
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy");
                fechaFormateada = formato.format(fechaUtil);
                personas.add(new Persona(res.getInt("id"), res.getString("nombre"), res.getString("apellido"),
                        res.getString("ci"), res.getString("celular"), res.getString("email"),
                        res.getString("tipo_persona"),
                        res.getString("estado"), fechaFormateada));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public String selectTotalMaterias(){
        String totalMaterias = "";
        String selectTotalMateriasSQL = "SELECT COUNT(*) FROM materia";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectTotalMateriasSQL)) {

            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {
                totalMaterias = res.getString("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalMaterias;
    }

    public String selectTotalEstudiantes(){
        String totalEstudiantes = "";
        String selectTotalMateriasSQL = "SELECT COUNT(*) FROM estudiante";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectTotalMateriasSQL)) {

            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {
                totalEstudiantes = res.getString("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalEstudiantes;
    }

    public String selectTotalDocentes(){
        String totalDocentes = "";
        String selectTotalMateriasSQL = "SELECT COUNT(*) FROM docente";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectTotalMateriasSQL)) {

            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {
                totalDocentes = res.getString("COUNT(*)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalDocentes;
    }

}
