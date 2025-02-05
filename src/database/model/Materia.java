package database.model;

public class Materia {
    // Atributos
    private int id;
    private String nombre;
    private String turno;
    private int idFacultad;
    private int idDocente;
    private String nombreFacultad;
    private String nombreDocente;

    // Constructor
    public Materia(int id, String nombre, String turno, int idFacultad, int idDocente, String nombreDocente, String nombreFacultad) {
        this.id = id;
        this.nombre = nombre;
        this.turno = turno;
        this.idFacultad = idFacultad;
        this.idDocente = idDocente;
        this.nombreFacultad = nombreFacultad;
        this.nombreDocente = nombreDocente;
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

    public int getIdFacultad() {
        return idFacultad;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    
}
