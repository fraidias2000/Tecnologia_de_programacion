/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo0;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Autobus {
  private Map <Integer, Asiento> asientos ;
  private String matricula;

  public Autobus(Scanner fichero, String ficheroAsientos )
                 throws FileNotFoundException{
    Scanner fautobuses = new Scanner(new File(ficheroAsientos));
    this.matricula = fichero.next();
    asientos = new HashMap<Integer, Asiento>();
    inicializarAsientos(fautobuses);
  }
  
  /*
   * Inicializa los asientos desde el fichero que le pasamos
   */
  public void inicializarAsientos(Scanner ficheroAsientos){
    while(ficheroAsientos.hasNextLine()) { 
        Asiento asiento = new Asiento(ficheroAsientos);
        asientos.put(asiento.getNumero(),asiento);      
    }
    ficheroAsientos.close();
  }
 
  /*
   *Ocupa un asiento de un autobus 
   */
  public boolean ocuparAsiento( int numeroAsiento, Viajero viajero){  
   
    Asiento asiento = asientos.get(numeroAsiento);
    if (asiento != null){
        asiento.ocupar(viajero);
        return true;
    }
    return false;
  }         
  
  /*
   * Desocupa un asiento de un autobus
   */
  public boolean desocuparAsiento( int numeroAsiento){  
    Asiento asiento = asientos.get(numeroAsiento);
    if (asiento != null){
        asiento.desocupar();
        return true;
    }
    return false;
  }
        
  /*
   *  Esccribe en un fichero todos los datos del viaje acerca del
   *  autobús y los asientos
   */
  public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
    fichero.write("Matricula: " + matricula + "\n");
    for (int i = 1; i < asientos.size() ; i++) {
        asientos.get(i).generarHojaViaje(fichero);    
    }
    fichero.close();
  }
  
  /*
   * Devuelve una cadena con toda la informacion sobre los asientos ocupados
   * y libres del autobus 
   */
  public String estadoOcupacion(){
    String cadena = "";
    for (int i = 1; i < asientos.size(); i++) {
        cadena = cadena + asientos.get(i).verEstadoOcupacion();
    }
    return cadena;
  }
}