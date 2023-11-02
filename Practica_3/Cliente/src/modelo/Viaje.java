/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;

import java.util.GregorianCalendar;

/**
 * Viaje en autobus
 * 
 */
public class Viaje {
    private int codigo;
    private String origen;
    private String destino;
    private GregorianCalendar fecha; 
    private Autobus autobus;
    
    /**
     * Constructor de un Viaje
     * 
     */
    public Viaje(int codigo, String origen, String destino, 
                    GregorianCalendar fecha, Autobus autobus) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.autobus = autobus;
    }
    
   /**
   * Devuelve el codigo 
   *
   */
    public int getCodigo() {
        return codigo;
    }
    /**
   *  Devuelve la fecha
   *
   */
    public GregorianCalendar getFecha() {
        return fecha;
    }
    
    /**
   *  Devuelve el autobus
   *
   */
    public Autobus getAutobus() {
        return autobus;
    }
    
    /**
     * Ocupa un asiento del autobus del viaje
     * 
     */
    public boolean ocuparAsiento(Autobus autobus, int numero, 
                                 Viajero viajero ) {
        return autobus.ocuparAsiento(numero, viajero);
          
    }
    
    /**
     * Desocupa un asiento del autobus del viaje
     * 
     */
    public boolean desocuparAsiento(Autobus autobus, int numero){  
           return autobus.desocuparAsiento(numero);
         
    }
    
    /**
     * Genera la hoja de viaje  
     */
    public String generarHoja(Autobus autobus) {
        return toString() + "\n" + autobus.generarHoja();
    }
    
    /**
     * Sobreescribe toString
     *
     */
    @Override
    public String toString() {      
        return Integer.toString(codigo) + " " + origen + "-" + destino;
    }
}