/*
 * Practica1.java 
 * Prototipo1
 * David Ros y Alvaro Fraidias
 * 11/03/2020
 */
package prototipo1;

import java.util.Scanner;

public class Viajero {
  private String nombre;
  private String DNI;

  public Viajero(String nombre, String DNI) {
    this.nombre = nombre;
    this.DNI = DNI;
    }     
 
  @Override
  public String toString(){
    String cadena = "Nombre: " + this.nombre  + "\n"
                      + "DNI: " + this.DNI;
      return cadena;
  
  }
}
