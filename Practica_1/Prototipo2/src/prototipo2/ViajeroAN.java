package prototipo2;

import java.util.Scanner;

/*
 * Practica1.java 
 * Prototipo0
 * David Ros y �?lvaro Fraidias
 * 27/02/2020
 */
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
                      + "DNI: " + this.DNI + "\n";
      return cadena;
  
  }
}
