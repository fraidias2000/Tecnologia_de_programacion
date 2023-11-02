/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package modelo;

/**
 * Asiento de un autobus
 * 
 */
public class Asiento {
    private int numero;
    private int fila;
    private int columna;
    private boolean ocupado; 
    private Viajero viajero;
    private static final String OCUPADO = "OCUPADO";
    private static final String LIBRE = "LIBRE";
  
    public Asiento(int numero, int fila, int columna, boolean ocupado) {
        this.numero = numero;
        this.fila = fila;
        this.columna = columna;
        this.ocupado = ocupado; 
    }
    
    /*
    * Devuelve el numero
    * 
    */
    public int getNumero() {
        return numero;
    }
    
    /*
    * Devuelve la fila
    * 
    */
    public int getFila() {
        return fila;
    }
    
    /*
    * Devuelve la columna
    * 
    */
    public int getColumna() {
        return columna;
    }
    
    /*
    * Devuelve si el asiento esta ocupado
    * 
    */
     public boolean isOcupado() {
        return ocupado;
    }
    
    /**
     * Asigna el asiento a un viajero
     * 
     */
    public void ocuparAsiento(Viajero viajero) {
        this.viajero = viajero;
        ocupado = true;
    }
    
    /**
     * Desasigna un asiento 
     * 
     */
    public void desocuparAsiento() {
        viajero = null; 
        ocupado = false;
    }
   
    
    /**
     * Obtiene el viajero del asiento
     * 
     */
    public Viajero obtenerViajero() {
        if (ocupado) {
            return viajero;
        }
        return null;
    }
    
    /**
     * Sobreescribe toString
     *
     */
    @Override
    public String toString() {
        String informacionAsiento;
        if (ocupado) {
            informacionAsiento = OCUPADO;
        }
        else {
            informacionAsiento = LIBRE;
        }
        return numero + " " + fila + " " + columna + 
               " " + informacionAsiento;
    }
}
