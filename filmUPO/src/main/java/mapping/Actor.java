
package mapping;

import java.util.Objects;


public class Actor {
    
    private int idActor;
    private String nombre;
    private String apellidos;
    private String nombreCompleto;

    public Actor(int idActor, String nombre, String apellidos) {
        this.idActor = idActor;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreCompleto = nombre + " "+ apellidos;
    }
    
    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
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

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actor other = (Actor) obj;
        if (this.idActor != other.idActor) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        return true;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    
    
    @Override
    public String toString() {
        return ""+ idActor;
    }
    
    
    
}
