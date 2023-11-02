package prototipo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Practica1.java 
 * Prototipo0
 * David Ros y �?lvaro Fraidias
 * 27/02/2020
 */
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
  public void inicializarAsientos(String ficheroAsientos) throws FileNotFoundException{
    Scanner fautobuses = new Scanner(new File(ficheroAsientos));
    int i = 0;
    while(fautobuses.hasNextLine()) { 
        asientos[i++] = new Asiento(fautobuses);
    }
    fautobuses.close();
  }
          
  /*
   *Ocupa un asiento de un autobus 
   */
  boolean ocuparAsiento(int numeroAsiento, Viajero viajero){  
      Asiento asiento = buscarAsiento(numeroAsiento);
      if (asiento != null){
          asiento.ocupar(viajero);
           return true;
      }
      return false;
  }
  public Asiento buscarAsiento(int numeroAsiento){
      for (int i = 0; i < MAX_ASIENTOS; i++) {
          if (asientos[i].getNumero() == numeroAsiento) {
              return asientos[i];
          }
         
      }
       return null;
  
  }
  
  /*
   * Desocupa un asiento de un autobus
   */
  boolean desocuparAsiento(int numeroAsiento){  
    Asiento asiento = buscarAsiento(numeroAsiento);
      if (asiento != null){
          asiento.desocupar();
           return true;
      }
      return false;
  }
        
  /*
   *  Genera una hoja con todos los datos del viaje
   */
  void generarHojaViaje(){}
        
  /*
   *  Comprueba los asientos disponibles de un autobus
   */
  
  public void estadoOcupacion(){}
  

  public String toString(){
    String cadena = "Matricula: " + this.matricula + "\n";
    for (int i = 0; i < 20; i++) {
        cadena = cadena + asientos[i].toString() + "\n";
    }
    return cadena;
  }
}