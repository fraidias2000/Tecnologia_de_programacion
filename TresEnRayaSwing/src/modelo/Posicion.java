/**
 * Posicion.java
 * ccatalan (01/2018) 
 *   
 */

package modelo;

/**
 *  Posición en el tablero de juego
 * 
 */
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
    
    /**
     *  toString()
     * 
     */
    public String toString() {
        return "(" + fila + ", " + columna + ")";    
    }
    
    /**
     *  equals()
     * 
     */    
    public boolean equals(Object obj) {
      if(this == obj) {
         return true;
      }
      if(!(obj instanceof Posicion)) {
         return false;
      }
      Posicion tmp = (Posicion)obj;
      
      return ((fila == tmp.fila) && (columna == tmp.columna));
    }

    /**
     *  hashCode()
     * 
     */        
    @Override
    public int hashCode() {
      int result = 17;
      result = 37 * result + fila;
      return 37 * result + columna;
    }
}
