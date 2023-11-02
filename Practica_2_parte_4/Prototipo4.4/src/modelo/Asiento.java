/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.4
 *10/05/2020 
 * 
 */
package modelo;

import Vista.OficinaVista;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
   
    private final String NUMERO = "Numero: ";
    private final String OCUPADO = " Ocupado: ";
    private final String LIBRE = " Libre \n";
 
   
    private final String OCUPAR_ASIENTO = "Ocupar Asiento";
  
    public Asiento(Scanner fichero){
        this.ocupado = false;
        this.viajero = null;
        this.numero = Integer.parseInt(fichero.next());
        this.fila = Integer.parseInt(fichero.next());
        this.columna = Integer.parseInt(fichero.next());

    }
    
   /*
    * Devuelde el numero
    */
    public int getNumero() {
        return numero;
    }

    /*
    * Devuelde la fila
    */
    public int getFila() {
        return fila;
    }

    /*
    * Devuelde la columna
    */
    public int getColumna() {
        return columna;
    }
  
  
    /*
    * Devuelde si está ocupado
    */
    public boolean ocupado(){
        return ocupado;
    } 

    /*
    * Devuelde el viajero
    */
    public Viajero getViajero() {
        return viajero;
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
                                  throws FileNotFoundException,
                                         IOException{ 
        fichero.write(NUMERO + numero);
        if(ocupado){
            fichero.write(" -->" + viajero.toString());
        }
        fichero.write("\n");
              
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