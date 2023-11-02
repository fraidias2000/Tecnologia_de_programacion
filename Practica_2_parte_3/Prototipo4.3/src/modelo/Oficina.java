/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
 * 
 */
package modelo;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import modelo.Viaje;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Oficina que contiene viajes.
 */

public class Oficina{
    private Map <Integer, Viaje> viajes;
    private PropertyChangeSupport observadores;
    private static Oficina instancia = null;
    private final String FICHEROSALIDA = "HojaViaje.txt";
    private final String CODIGO_VIAJE = "Codigo Viaje: ";
  
   public Oficina(String ficheroViajes, String ficheroAutobuses) 
                   throws FileNotFoundException{
        observadores = new PropertyChangeSupport(this);
        Scanner fviajes = new Scanner(new File(ficheroViajes)); 
        viajes = new HashMap<Integer, Viaje>();
        recuperarFicheroViajes(fviajes, ficheroAutobuses);
        
       
    }
   
   public int ViajesTotales(){
       return viajes.size();
   }
   
   /*
    * Comprueba que no se ha instanciado ningún objeto Oficina 
    *y si es así, lo instancia.
    */
   public static Oficina instancia(String ficheroViajes, 
                                   String ficheroAutobuses) 
                                   throws FileNotFoundException {
        if (instancia == null)
        instancia = new Oficina( ficheroViajes, ficheroAutobuses);
        return instancia;
    }
   
   
 
    /*
     * Ocupa un asiento de un autobus de un viaje
     */
    public boolean ocuparAsiento(int codigoViaje,
                                 int numeroAsiento, Viajero unViajero){
        Viaje viaje = viajes.get(codigoViaje);
        if(viaje != null){
            viaje.ocuparAsiento(numeroAsiento, unViajero);    
            return true;
        }
        return false;
    }
    
    /*
     * Desocupa un asiento de un autobus de un viaje
     */
    public boolean desocuparAsiento(int codigoViaje, int numeroAsiento){
      Viaje viaje = viajes.get(codigoViaje);
        if(viaje != null){
            viaje.desocuparAsiento(numeroAsiento);
            return true;
        }
        return false;
    }
    
    /*
     * Genera un fichero con todos los datos relacionados con un viaje
     */
    public boolean generarHoja(int codigoViaje) throws IOException{
        String sFichero = FICHEROSALIDA ;
        File fichero = new File(sFichero);
        BufferedWriter hojaViaje = new BufferedWriter
                                       (new FileWriter(sFichero));
        Viaje viaje = viajes.get(codigoViaje);
        if(viaje != null){  
            if (fichero.exists()) {
                viaje.generarHojaViaje(hojaViaje);
                hojaViaje.close();
                return true;
            }      
        }
        return false; 
    } 
    
    /*
     * Lee de los ficheros pasados como parametros e inicializa los
     * viajes y las posiciones de los asientos en los autobuses
     */
    public void recuperarFicheroViajes(Scanner ficheroViajes,
                                       String ficheroAutobuses) 
                                       throws FileNotFoundException{ 
        while(ficheroViajes.hasNextLine()) {          
            Viaje viaje = new Viaje(ficheroViajes, ficheroAutobuses, observadores);
            viajes.put(viaje.getCodigo(), viaje); 
        }        
        ficheroViajes.close();
    }
    
    /*
    * Devuelve una cadena con toda la informacion sobre los asientos
    * ocupados y libres de todos los viajes 
    */
    public String obtenerEstadoOcupacion(){
        String cadena = "";
        for (int i = 0; i < viajes.size(); i++) {
            cadena = cadena + CODIGO_VIAJE + 
                     viajes.get(i).getCodigo() +
                     viajes.get(i).obtenerEstadoOcupacion();         
        } 
        return cadena;
    } 
    /*
     * Dado un vector, lo rellena con los viajes de un oficina
     */
    public Viaje[] obtenerViajes(Viaje viaje[]){ 
         for (int i = 0; i < viajes.size(); i++) {
             viaje[i] = viajes.get(i);
         }
        return viaje;
    }
    
    /**
  *  Añade nuevo observador de la oficina 
  */     
  public void nuevoObservador(PropertyChangeListener observador) {
    this.observadores.addPropertyChangeListener(observador);
  }  
    

}