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

import database.model.Persona;
import database.model.Facultad;
import database.model.Materia;

public class Query {
    private Conection connection;

    public Query() {
        connection = new Conection();
    }

    public void insertarPersona(String nombre, String apellido, String ci, String celular, String email,
            String tipoPersona, String carrera, LocalDate fechaActual) {
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

    public ArrayList<Persona> selectTodosLosDocentes() {
        ArrayList<Persona> docentes = new ArrayList<>();
        String selectPersonasSQL = "SELECT * FROM persona where tipo_persona = 'Docente'";
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
                docentes.add(new Persona(res.getInt("id"), res.getString("nombre"), res.getString("apellido"),
                        res.getString("ci"), res.getString("celular"), res.getString("email"),
                        res.getString("tipo_persona"),
                        res.getString("estado"), fechaFormateada));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    public ArrayList<Persona> selectTodosLosEstudiantes() {
        ArrayList<Persona> docentes = new ArrayList<>();
        String selectPersonasSQL = "SELECT * FROM persona where tipo_persona = 'Estudiante'";
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
                docentes.add(new Persona(res.getInt("id"), res.getString("nombre"), res.getString("apellido"),
                        res.getString("ci"), res.getString("celular"), res.getString("email"),
                        res.getString("tipo_persona"),
                        res.getString("estado"), fechaFormateada));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    public ArrayList<Materia> selectTodasLasMaterias() {
        ArrayList<Materia> materias = new ArrayList<>();
        String selectPersonasSQL = "SELECT m.*, f.nombre AS nombre_facultad, p.nombre AS nombre_docente, COUNT(i.id_estudiante) AS cantidad_inscritos FROM facultad f JOIN materia m ON m.id_facultad = f.id JOIN docente d ON m.id_docente = d.id JOIN persona p ON d.id_persona = p.id LEFT JOIN inscripcion i ON m.id = i.id_materia GROUP BY m.id, f.nombre, p.nombre";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectPersonasSQL)) {

            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {

                materias.add(new Materia(res.getInt("id"),
                        res.getString("nombre"),
                        res.getString("turno"),
                        res.getInt("id_facultad"),
                        res.getInt("id_docente"),
                        res.getString("nombre_docente"),
                        res.getString("nombre_facultad"),
                        res.getInt("cantidad_inscritos")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    public ArrayList<Materia> selectMateriasPorNombre(String nombreMateria) {
        ArrayList<Materia> materias = new ArrayList<>();
        String selectMateriasSQL = "SELECT m.*, f.nombre AS nombre_facultad, p.nombre AS nombre_docente, COUNT(i.id_estudiante) AS cantidad_inscritos "
                +
                "FROM facultad f " +
                "JOIN materia m ON m.id_facultad = f.id " +
                "JOIN docente d ON m.id_docente = d.id " +
                "JOIN persona p ON d.id_persona = p.id " +
                "LEFT JOIN inscripcion i ON m.id = i.id_materia " +
                "WHERE m.nombre like ? " + // Filtro por nombre de la materia
                "GROUP BY m.id, f.nombre, p.nombre";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectMateriasSQL)) {

            pstmt.setString(1, "%" + nombreMateria + "%");

            ResultSet res = pstmt.executeQuery();

            while (res.next()) {
                materias.add(new Materia(
                        res.getInt("id"),
                        res.getString("nombre"),
                        res.getString("turno"),
                        res.getInt("id_facultad"),
                        res.getInt("id_docente"),
                        res.getString("nombre_facultad"),
                        res.getString("nombre_docente"),
                        res.getInt("cantidad_inscritos")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materias;
    }

    public ArrayList<Facultad> selectTodasLasFacultades() {
        ArrayList<Facultad> facultades = new ArrayList<>();
        String selectPersonasSQL = "SELECT * FROM facultad";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectPersonasSQL)) {

            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {

                facultades.add(new Facultad(res.getInt("id"), res.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultades;
    }

    public void insertIntoInscripcion(int id_estudiante, int id_materia) {
        String insertSQL = "INSERT INTO inscripcion (id_estudiante, id_materia) VALUES ((SELECT id FROM estudiante WHERE id_persona = ? LIMIT 1), ?)";
        System.out.println(id_estudiante);
        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setInt(1, id_estudiante);
            pstmt.setInt(2, id_materia);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("exito.");
            } else {
                System.out.println("fracaso.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String selectTotalMaterias() {
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

    public String selectTotalEstudiantes() {
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

    public String selectTotalDocentes() {
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

    public String selectTotalFacultades() {
        String totalDocentes = "";
        String selectTotalMateriasSQL = "SELECT COUNT(*) FROM facultad";

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

    public ArrayList<Persona> selectDocenteCon(String campo, String parametro) {
        ArrayList<Persona> docentes = new ArrayList<>();
        String selectPersonasSQL = "SELECT * FROM persona WHERE " + campo + " LIKE ? and tipo_persona = 'Docente'";
        Date fechaSQL;
        String fechaFormateada;

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectPersonasSQL)) {
            pstmt.setString(1, "%" + parametro + "%");
            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {
                fechaSQL = res.getDate("fecha_registro");
                // Convertir a java.util.Date
                java.util.Date fechaUtil = new java.util.Date(fechaSQL.getTime());

                // Formatear a "dd-MM-yy"
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy");
                fechaFormateada = formato.format(fechaUtil);
                docentes.add(new Persona(res.getInt("id"), res.getString("nombre"), res.getString("apellido"),
                        res.getString("ci"), res.getString("celular"), res.getString("email"),
                        res.getString("tipo_persona"),
                        res.getString("estado"), fechaFormateada));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return docentes;
    }

    public ArrayList<Persona> selectEstudianteCon(String campo, String parametro) {
        ArrayList<Persona> estudiantes = new ArrayList<>();
        String selectPersonasSQL = "SELECT * FROM persona WHERE " + campo + " LIKE ? and tipo_persona = 'Estudiante'";
        Date fechaSQL;
        String fechaFormateada;

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(selectPersonasSQL)) {
            pstmt.setString(1, "%" + parametro + "%");
            pstmt.execute();
            ResultSet res = pstmt.getResultSet();

            while (res.next()) {
                fechaSQL = res.getDate("fecha_registro");
                // Convertir a java.util.Date
                java.util.Date fechaUtil = new java.util.Date(fechaSQL.getTime());

                // Formatear a "dd-MM-yy"
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy");
                fechaFormateada = formato.format(fechaUtil);
                estudiantes.add(new Persona(res.getInt("id"), res.getString("nombre"), res.getString("apellido"),
                        res.getString("ci"), res.getString("celular"), res.getString("email"),
                        res.getString("tipo_persona"),
                        res.getString("estado"), fechaFormateada));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return estudiantes;
    }

    public void insertIntoMateria(String nombre, String turno, String facultad, int idPersona) {
        String insertSQL = "INSERT INTO materia (nombre, turno, id_facultad, id_docente) " +
                "VALUES (?, ?, (SELECT id FROM facultad WHERE nombre = ?), " +
                "(SELECT d.id FROM docente d " +
                "JOIN persona p ON p.id = d.id_persona " +
                "WHERE p.id = ?))";

        try (Connection conn = connection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, turno);
            pstmt.setString(3, facultad);
            pstmt.setInt(4, idPersona);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Materia insertada exitosamente.");
            } else {
                System.out.println("No se pudo insertar la materia.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
