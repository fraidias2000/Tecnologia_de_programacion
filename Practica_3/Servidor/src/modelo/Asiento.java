/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;

import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * Asientos existentes en un autobús.
 */
public class Asiento {
    private Viajero viajero;
    private int ocupado; //libre = 0, ocupado = 1, pasillo = 2
    private int fila;
    private int columna;
    private int numero;
    private final int LIBRE_NUMERO = 0;
    private final int OCUPADO_NUMERO = 1;
    private final int PASILLO_NUMERO = 2;
    private final String NUMERO = "Numero: ";
    private final String OCUPADO = "ocupado";
    private final String LIBRE = "libre";
    private final String PASILLO = "pasillo";
 
    private PropertyChangeSupport observadores;
    private final String OCUPAR_ASIENTO = "Ocupar Asiento";
  
    public Asiento(Scanner fichero, PropertyChangeSupport observador){
        this.viajero = null;       
        this.numero = Integer.parseInt(fichero.next());
        this.fila = Integer.parseInt(fichero.next());
        this.columna = Integer.parseInt(fichero.next());
        this.observadores = observador;
        
        int tipoAsiento = fichero.nextInt();
        if(tipoAsiento == LIBRE_NUMERO){ 
            this.ocupado = LIBRE_NUMERO;
            if(fichero.hasNextLine()){
                fichero.nextLine();
            }
        }
        else if(tipoAsiento == PASILLO_NUMERO){ 
            this.ocupado = PASILLO_NUMERO;
            if(fichero.hasNextLine()){
                fichero.nextLine();
            }    
        }
        else if(tipoAsiento == OCUPADO_NUMERO){ 
            this.ocupado = OCUPADO_NUMERO;
            String nombre = fichero.next();
            String dni = fichero.next();
            this.viajero = new Viajero(nombre, dni);
            if(fichero.hasNextLine()){
                fichero.nextLine();
            } 
        }
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
    public int getEstado() {
        return ocupado;
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
    public int ocupado(){
        return ocupado;
    } 

    /*
    * Devuelde el viajero
    */
    public Viajero getViajero() {
        if (ocupado == OCUPADO_NUMERO) {
            return viajero;
        }
        return null;
    }
  
   /*
    * Ocupa el asiento de un autobus 
    */
    public boolean ocupar(Viajero unViajero){ 
        if(ocupado ==  LIBRE_NUMERO){
            viajero = unViajero;
            ocupado = OCUPADO_NUMERO;           
            return true;
        }
        return false; 
    }
  
    /*
    * Desocupa el asiento 
    */
    public boolean desocupar(){
        if(ocupado == OCUPADO_NUMERO ){
            viajero = null;
            ocupado = LIBRE_NUMERO;          
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
        if(ocupado == OCUPADO_NUMERO){
            fichero.write(" -->" + viajero.toString());
        }
        fichero.write("\n");         
    }
   /*
    * Devuelve una cadena con información sobre si un asiento esta ocupado
    */
    public String obtenerEstadoOcupacion(){
        String cadena = "";
        if(ocupado == OCUPADO_NUMERO){
            cadena = OCUPADO;
        }
        if(ocupado == LIBRE_NUMERO){
            cadena = LIBRE;
        }
        if(ocupado == PASILLO_NUMERO){
            cadena = PASILLO;
        }
        return cadena;
    }
    
}