/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;

//import Vista.OficinaVista;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
    public static final String OCUPAR = "Ocupar";
    public static final String DESOCUPAR = "Desocupar";
    private int numeroAsientos = 0;
    private int filas = 0;
    private int columnas = 0;
    private int OCUPADO = 1;

    public Autobus(Scanner fichero, String ficheroAsientos)
                   throws FileNotFoundException, IOException{
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

    public String getFilas() {
        return String.valueOf(filas);
    }

    public String getColumnas() {
        return String.valueOf(columnas);
    }
    
    
    public Viajero obtenerViajero(int numeroAsiento) {
        return asientos.get(numeroAsiento).getViajero();
    }

    public String getDatosAsiento(int numero) {
         if(asientos.get(numero).getEstado() == OCUPADO){
            return asientos.get(numero).getNumero() + " " +
                   asientos.get(numero).getFila() + " " +
                   asientos.get(numero).getColumna() + " " +
                   asientos.get(numero).getEstado() + " " + 
                   asientos.get(numero).getViajero().toString();
        }     
        return  asientos.get(numero).getNumero() + " " +
                asientos.get(numero).getFila() + " " +
                asientos.get(numero).getColumna() + " " +
                asientos.get(numero).getEstado();
       
    }
    
  
   /*
    * Inicializa los asientos desde el fichero que le pasamos
    */
    public void inicializarAsientos(Scanner ficheroAsientos){
         numeroAsientos = ficheroAsientos.nextInt();     
         filas = ficheroAsientos.nextInt();
         columnas = ficheroAsientos.nextInt();
        while(ficheroAsientos.hasNextLine()) {       
            Asiento asiento = new Asiento(ficheroAsientos, observadores);
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
    public void generarHojaViaje(BufferedWriter  fichero) 
                                 throws FileNotFoundException, IOException{    
        fichero.write(MATRICULA + matricula + "\n");
        for (int i = 0; i <= asientos.size()-1 ; i++) {
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
        for (int i = 0; i < asientos.size()-1; i++) {
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