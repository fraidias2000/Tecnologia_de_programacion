/*
 * Practica1.java 
 * Prototipo1
 * David Ros y Alvaro Fraidias
 * 11/03/2020
 */

package prototipo1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Autobus {
  private final int MAX_ASIENTOS = 20;
  private Asiento[] asientos;
  private String matricula;

  public Autobus(Scanner fichero){
    this.matricula = fichero.next();
    asientos = new Asiento[MAX_ASIENTOS];
  }
  
  /*
   * Inicializa los asientos desde el fichero que le pasamos
   */
  public void inicializarAsientos(String ficheroAsientos) 
                                  throws FileNotFoundException{
    Scanner fautobuses = new Scanner(new File(ficheroAsientos));
    int i = 0;
    while(fautobuses.hasNextLine()) { 
        asientos[i++] = new Asiento(fautobuses);
    }
  }
          
  /*
   *Ocupa un asiento de un autobus 
   */
  boolean ocuparAsiento(int numeroAsiento, Viajero viajero){ 
    return true;
  }
        
  /*
   * Desocupa un asiento de un autobus
   */
  boolean desocuparAsiento(int numeroAsiento){ 
    return true;
  }
        
  /*
   *  Genera una hoja con todos los datos del viaje
   */
  void generarHojaViaje(){}
        
  /*
   *  Comprueba los asientos disponibles de un autobus
   */
  //void comprobarEstadoOcupacion(){}
  
  public void estadoOcupacion(){}
  

  public String toString(){
    String cadena = "Matricula: " + this.matricula + "\n";
    for (int i = 0; i < 20; i++) {
        cadena = cadena + asientos[i].toString() + "\n";
    }
    return cadena;
  }
}