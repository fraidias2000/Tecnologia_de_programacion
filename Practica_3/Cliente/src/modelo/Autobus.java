/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package modelo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Autobus que realiza un viaje
 * 
 */
public class Autobus {
    private String matricula;
    private Map<Integer, Asiento> asientos;
    
    private static final String ASIENTO_OCUPADO = "OCUPADO";
    private static final String ASIENTO_LIBRE = "LIBRE";
    

    public Autobus(String matricula) {
        this.matricula = matricula;
        asientos = new HashMap();
    }
    
    /*
    * Devuelve la matricula
    * 
    */
    public String getMatricula() {
        return matricula;
    }
    
    /*
    * Devuelve los asientos
    * 
    */
    public Map<Integer, Asiento> getAsientos() {
        return asientos;
    }
     
   /*
    * Ocupa un asiento
    */
    public boolean ocuparAsiento(int numero, Viajero viajero) {
        Asiento asiento = asientos.get(numero);     
        if (!asiento.isOcupado()) {
            asiento.ocuparAsiento(viajero);
            return true;
        }
        return false;
    }
    
    /*
     * Desocupa un asiento
     */
    public boolean desocuparAsiento(int numero) {
        Asiento asiento = asientos.get(numero);
        
        if (asiento.isOcupado()) {
            asiento.desocuparAsiento();
            return true;
        }
        return false;
    }
    
    /**
     * Devuelve el estado de ocupacion de cada asiento
     * 
     */
    public String obtenerEstadoOcupacion() {
        String informacionEstado = null ;
        
        Set<Map.Entry<Integer, Asiento>> asientos = 
                this.asientos.entrySet();
        
        for (Map.Entry<Integer, Asiento> asiento: asientos) {
            if (asiento.getValue().isOcupado()) {
                informacionEstado = informacionEstado + 
                        (asiento.getValue().getNumero() + ":" + 
                        ASIENTO_OCUPADO + ":" + 
                        asiento.getValue().obtenerViajero() + "\n");
            }
            else {
                 informacionEstado = informacionEstado + 
                         (asiento.getValue().getNumero() + ":" + 
                        ASIENTO_LIBRE + "\n");
            }
        }
        return informacionEstado;
    }
    
    /**
     * Genera la hoja de viaje  
     */
    public String generarHoja() {
        return obtenerEstadoOcupacion();
    }
    
    /*
     * Sobreescribe toString
     */
    @Override
    public String toString() {
        return matricula;
    }    
}