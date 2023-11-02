/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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
  }
  
  /*
   * Ocupa el asiento de un autobus 
   */
  public boolean ocupar(Viajero unViajero){ 
    if( ! ocupado){
        viajero = unViajero;
        ocupado = true;
        return true;
    }
    return false; 
  }

  public int getNumero() {
    return numero;
    }
  
  /*
   * Desocupa el asiento de un autobus 
   */
  public boolean ocupado(){
    return ocupado;
  }
  public boolean desocupar(){
    if(ocupado){
        viajero = null;
        ocupado = false;
        return true;
    }
    return false;
  }

  /*
   *  Comprueba los asientos disponibles de un autobus
   */
  public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
    if(ocupado){
        fichero.write("Numero: " + numero + " Ocupado: " );
        viajero.generarHojaViaje(fichero);
    }else{
        fichero.write("Numero: " + numero + " Libre \n");
              
    }
      
  }
  public String verEstadoOcupacion(){
    String cadena = "";
    if(ocupado){
        cadena = "x";
    }else{
        cadena = "-";
    }
    return cadena;
  }
  
  /*public String toString(){
      String cadena = "Numero Asiento: " + numero + "\n"
                    + "Fila: " + fila + "\n"
                    + "Columna: " + columna + "\n"
                    + "Ocupado: " + ocupado + "\n";
      if(ocupado){
          cadena = cadena + viajero.toString();
      }
      return cadena; 
  }*/
}