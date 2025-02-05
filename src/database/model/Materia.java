package database.model;

public class Materia {
    // Atributos
    private int id;
    private String nombre;
    private String turno;
    private String idFacultad;
    private String idDocente;

    // Constructor
    public Materia(int id, String nombre, String turno, String idFacultad, String idDocente) {
        this.id = id;
        this.nombre = nombre;
        this.turno = turno;
        this.idFacultad = idFacultad;
        this.idDocente = idDocente;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTurno() {
        return turno;
    }

    public String getIdFacultad() {
        return idFacultad;
    }

    public String getIdDocente() {
        return idDocente;
    }
}
