/*
 * Practica1.java 
 * Prototipo1
 * David Ros y Alvaro Fraidias
 * 11/03/2020
 */
package prototipo1;

import java.util.Scanner;

public class Asiento {
  private Viajero viajero;
  private boolean ocupado;
  private int fila;
  private int columna;
  private int numero;

  public Asiento(Scanner fichero) {
    this.ocupado = false;
    this.viajero = null;
    this.numero = fichero.nextInt();
    this.fila = fichero.nextInt();
    this.columna = fichero.nextInt();
    fichero.nextLine();
  }
  
  /*
   * Ocupa el asiento de un autobus 
   */
  boolean ocupar(Viajero viajero){ 
    return true;
  }

  /*
   * Desocupa el asiento de un autobus 
   */
  boolean desocupar(Viajero viajero){ 
    return true;
  }

  
  /*
   *  Comprueba los asientos disponibles de un autobus
   */
  void verEstadoOcupacion(){}
  
  public String toString(){
      String cadena = "Numero Asiento: " + numero + "\n"
                    + "Fila: " + fila + "\n"
                    + "Columna: " + columna + "\n";
      return cadena;
  
  }
}
