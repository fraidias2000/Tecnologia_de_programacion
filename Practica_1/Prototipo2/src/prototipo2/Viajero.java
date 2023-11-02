/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo3;

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
 
  public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
    fichero.write("Nombre: " + nombre + " DNI: " + DNI + "\n");  
  } 
  /* 
  @Override
  public String toString(){
      String cadena = "Nombre: " + this.nombre  + "\n"
                      + "DNI: " + this.DNI + "\n";
      return cadena; 
  }
 */
}