package Components.Admin;

public class Persona {
    private String nombre;
    private String apellidos;
    private String ci;
    private String celular;
    private String tipoPersona;
    private String correo;
    private String carrera;
    private String estado;

    public Persona(String nombre, String apellidos, String ci, String celular, String carrera, String correo,
            String tipoPersona, String estado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.ci = ci;
        this.celular = celular;
        this.carrera = carrera;
        this.correo = correo;
        this.tipoPersona = tipoPersona;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public String getCi() {
        return ci;
    }

    public String getCelular() {
        return celular;
    }

    public String getEstado(){
        return estado;
    }

}
