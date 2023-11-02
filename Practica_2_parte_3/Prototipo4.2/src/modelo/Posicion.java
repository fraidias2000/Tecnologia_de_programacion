/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
 * 
 */
package modelo;

public class Posicion {
    private int fila;
    private int columna;
    /**
     *  Construye una posición
     *  
     */  
    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
  
    /**
     *  Devuelve la fila de la posición
     * 
     */   
    public int devolverFila() {
        return fila;
    }
    /**
     *  Devuelve la columna de la posición
     * 
     */   
    public int devolverColumna() {
        return columna;
    }
}
