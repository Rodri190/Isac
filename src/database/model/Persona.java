package database.model;

public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private String ci;
    private String celular;
    private String email;
    private String tipoPersona;
    private String estado;
    private String fechaRegistro;

    public Persona(int id, String nombre, String apellido, String ci, String celular, String email, String tipoPersona, String estado, String fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ci = ci;
        this.celular = celular;
        this.email = email;
        this.tipoPersona = tipoPersona;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCi() {
        return ci;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaRegistro(){
        return fechaRegistro;
    }
}

