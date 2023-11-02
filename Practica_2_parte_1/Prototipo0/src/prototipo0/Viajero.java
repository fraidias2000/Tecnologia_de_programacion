/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo0;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;


public class Viajero {
  private String nombre;
  private String DNI;

  public Viajero(String nombre, String DNI) {
    this.nombre = nombre;
    this.DNI = DNI;
  }     
 
 /*
  * Escribe en el fichero pasado como argumento informacion sobre un viajero
  */
  public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
    fichero.write("Nombre: " + nombre + " DNI: " + DNI + "\n");  
  } 
}