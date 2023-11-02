package prototipo2;

import java.util.Scanner;

/*
 * Practica1.java 
 * Prototipo0
 * David Ros y Alvaro Fraidias
 * 27/02/2020
 */
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
  
  public void verEstadoOcupacion(){}
  
  public String toString(){
      String cadena = "Numero Asiento: " + numero + "\n"
                    + "Fila: " + fila + "\n"
                    + "Columna: " + columna + "\n"
                    + "Ocupado: " + ocupado + "\n";
      if(ocupado){
          cadena = cadena + viajero.toString();
      }
      return cadena;
  
  }
}
