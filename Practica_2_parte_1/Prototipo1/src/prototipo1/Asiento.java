/*
 * Practica2.java 
 * Prototipo0
 * David Ros y alvaro Fraidias
 * 29/03/2020
 */
package prototipo1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*
 * Asientos existentes en un autobús.
 */

public class Asiento {
   private Viajero viajero;
  private boolean ocupado;
  private int fila;
  private int columna;
  private int numero;
  private final String OCUPADO = "x";
  private final String LIBRE = "-";
  private final String NUMERO = "Numero: ";
  private final String OCUP = " Ocupado: ";
  private final String LIB = " Libre \n";

  public Asiento(Scanner fichero) {
    this.ocupado = false;
    this.viajero = null;
    this.numero = fichero.nextInt();
    this.fila = fichero.nextInt();
    this.columna = fichero.nextInt();
  }
  
  public int getNumero() {
    return numero;
  }
  
  public boolean ocupado(){
    return ocupado;
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
  
 /*
  * Desocupa el asiento 
  */
  public boolean desocupar(){
    if(ocupado){
        viajero = null;
        ocupado = false;
        return true;
    }
    return false;
  }

 /*
  *  Escribe en un fichero los asientos de un autobus junto 
  *  scon su disponibilidad
  */
  public void generarHojaViaje(BufferedWriter  fichero) 
                                                throws IOException{
    if(ocupado){
        fichero.write(NUMERO + numero + OCUP );
        viajero.generarHojaViaje(fichero);
    }else{
        fichero.write(NUMERO + numero + LIB);         
    }    
  }
  
 /*
  * Devuelve una cadena con información sobre si un asiento esta ocupado
  */
  public String obtenerEstadoOcupacion(){
    String cadena = "";
    if(ocupado){
        cadena = OCUPADO;
    }else{
        cadena = LIBRE;
    }
    return cadena;
  }
}