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
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Viaje {
    private int codigo;
    private Autobus autobus;
    private String lugarOrigen;
    private String lugarDestino;
    private String fecha;

    public Viaje(Scanner fichero, String ficheroAutobus) throws FileNotFoundException {
        this.autobus = new Autobus(fichero, ficheroAutobus); 
        this.codigo = fichero.nextInt();
        this.lugarOrigen = fichero.next();
        this.lugarDestino = fichero.next();
        this.fecha = fichero.next();
    }

    public int getCodigo() {
        return codigo;
    }
           
    /*
     * Ocupa un asiento de un autbus
     */
    public boolean ocuparAsiento( int numeroAsiento, Viajero viajero){
        return autobus.ocuparAsiento(numeroAsiento, viajero);  
    }
        
    /*
     * Desocupa un asiento de un autobus
     */
    public boolean desocuparAsiento( int numeroAsiento){
        return autobus.desocuparAsiento(numeroAsiento);    
    }
        
    /*
     *  Genera una hoja con todos los datos del viaje
     */
    public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
        fichero.write("Codigo Viaje: " + codigo + "\nLugar de origen: " + lugarOrigen + 
                      "\nLugar destino: " + lugarDestino + "\nFecha: " + fecha + "\n");
        autobus.generarHojaViaje(fichero);
    }
        
    /*
     * Comprueba los asientos que estan libres y ocupados
     */
    public String comprobarEstadoOcupacion(){
        String cadena = "";
        cadena = autobus.estadoOcupacion() + "\n";
        return cadena;
    } 
    
   /* public String toString(){
        String cadena = "Codigo: " + this.codigo +
                        ", Lugar de origen: " + this.lugarOrigen +
                        ", Lugar de destino: " + this.lugarDestino + 
                        ", Fecha: " + this.fecha + "\n" + autobus.toString();
        return cadena;
    }
    */
}