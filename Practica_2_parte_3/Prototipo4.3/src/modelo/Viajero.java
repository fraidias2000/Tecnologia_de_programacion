/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
 * 
 */
package modelo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

/*
 * Persona que realiza un viaje.
 */

public class Viajero {
  private String nombre;
  private String dni;
  private final String NOMBRE = " Nombre: ";
  private final String DNI = " DNI: ";

  public Viajero(String nombre, String dni) {
    this.nombre = nombre;
    this.dni = dni;
  }     

    @Override
    public String toString() {
        return DNI + dni + NOMBRE + nombre;
    }

 /*
  * Escribe en el fichero pasado como argumento informacion sobre 
  *  un viajero
  */
  public void generarHojaViaje(BufferedWriter  fichero) 
                                                throws IOException{
    fichero.write(NOMBRE + nombre + DNI + dni + "\n");  
  } 
}