package database.model;


public class Facultad {
    private int id;
    private String nombre;

    public Facultad(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre(){
        return nombre;
    }

    public int getId(){
        return id;
    }

    
}
