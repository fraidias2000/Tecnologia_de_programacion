/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;

public class Viajero {
    private String dni;
    private String nombre;

    public Viajero(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    } 

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }
    
    
    /**
     * Sobreescribe toString
     *
     */
    @Override
    public String toString() {
        return nombre + " " + dni;
    }
}
