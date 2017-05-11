
package mapping;

import java.util.Objects;


public class Director {
    
    private int idDirector;
    private String nombre;
    private String apellidos;
    private String nombreCompleto;

    public Director(int idDirector, String nombre, String apellidos) {
        this.idDirector = idDirector;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreCompleto = nombre + " " + apellidos;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
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
        int hash = 7;
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
        final Director other = (Director) obj;
        if (this.idDirector != other.idDirector) {
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
        return " " + idDirector;
    }
    
    
}
