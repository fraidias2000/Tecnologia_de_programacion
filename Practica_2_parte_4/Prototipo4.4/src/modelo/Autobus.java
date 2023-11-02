/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.4
 *10/05/2020 
 * 
 */
package modelo;

import Vista.OficinaVista;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Autubús dispoible en un viaje.
 */
public class Autobus {
    private Map <Integer, Asiento> asientos ;
    private String matricula;
    private final String MATRICULA = "Matricula: ";
    private PropertyChangeSupport observadores;

    public Autobus(Scanner fichero, String ficheroAsientos,
                   PropertyChangeSupport observador )
                   throws IOException{
        this.observadores = observador;
        Scanner fautobuses = new Scanner(new File(ficheroAsientos));
        this.matricula = fichero.next();
        asientos = new HashMap<Integer, Asiento>();
        inicializarAsientos(fautobuses);
    }
  
   /*
    * Devuelve el numero total de asientos
    */
    public int AsientosTotales(){
        return asientos.size();
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
            this.observadores.firePropertyChange(OficinaVista.OCUPAR,null,
                                                 viajero);
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
            this.observadores.firePropertyChange(OficinaVista.DESOCUPAR, 
                                               asiento.getViajero(), null);
            return true;
        }
        return false;
    }
        
   /*
    *  Esccribe en un fichero todos los datos del viaje acerca del
    *  autobús y los asientos
    */
    public void generarHojaViaje(BufferedWriter  fichero) 
                                 throws IOException{    
        fichero.write(MATRICULA + matricula + "\n");
        for (int i = 1; i <= asientos.size() ; i++) {
            asientos.get(i).generarHojaViaje(fichero);    
        }      
        fichero.close();
    }
  
   /*
    * Devuelve una cadena con toda la informacion sobre los asientos
    * ocupados y libres del autobus 
    */
    public String estadoOcupacion(){
        String cadena = "";
        for (int i = 1; i < asientos.size(); i++) {
            cadena = cadena + asientos.get(i).obtenerEstadoOcupacion();
        }
        return cadena;
    }
  
    /*
    * Devuelve un asiento en funcion a un numero de asiento
    */
    public Asiento obtenerAsiento(int numeroAsiento){
        return asientos.get(++numeroAsiento);
    }
}