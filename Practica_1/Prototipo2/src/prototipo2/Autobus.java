/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Autobus {
  private int numeroAsientos = 0;
  private Asiento[] asientos;
  private String matricula;

  public Autobus(Scanner fichero, String ficheroAsientos ) throws FileNotFoundException{
    Scanner fautobuses = new Scanner(new File(ficheroAsientos));
    this.matricula = fichero.next();
    this.numeroAsientos = fautobuses.nextInt();
    fautobuses.nextLine();
    asientos = new Asiento[numeroAsientos];
    inicializarAsientos(fautobuses);
  }
  
  /*
   * Inicializa los asientos desde el fichero que le pasamos
   */
  public void inicializarAsientos(Scanner ficheroAsientos){
    int i = 0;
    while(ficheroAsientos.hasNextLine()) { 
        asientos[i++] = new Asiento(ficheroAsientos);
        
    }
    ficheroAsientos.close();
  }
 
  /*
   *Ocupa un asiento de un autobus 
   */
  public boolean ocuparAsiento(int numeroAsiento, Viajero viajero){  
    Asiento asiento = buscarAsiento(numeroAsiento);
    if (asiento != null){
        asiento.ocupar(viajero);
        return true;
    }
    return false;
  }
  public Asiento buscarAsiento(int numeroAsiento){
    for (int i = 0; i < numeroAsientos; i++) {
        if (asientos[i].getNumero() == numeroAsiento) {
            return asientos[i];
        }     
    }
    return null;
  
  }
  
  /*
   * Desocupa un asiento de un autobus
   */
  public boolean desocuparAsiento(int numeroAsiento){  
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
  public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
    fichero.write("Matricula: " + matricula + "\n");
    for (int i = 0; i < numeroAsientos ; i++) {
        asientos[i].generarHojaViaje(fichero);    
    }
    fichero.close();
  }
        
  /*
   *  Comprueba los asientos disponibles de un autobus
   */
  
  public String estadoOcupacion(){
    String cadena = "";
    for (int i = 0; i < numeroAsientos; i++) {
        cadena = cadena + asientos[i].verEstadoOcupacion();
    }
    return cadena;
  }
  
/*
  public String toString(){
    String cadena = "Matricula: " + this.matricula + "\n";
    for (int i = 0; i < 20; i++) {
        cadena = cadena + asientos[i].toString() + "\n";
    }
    return cadena;
  }*/
}