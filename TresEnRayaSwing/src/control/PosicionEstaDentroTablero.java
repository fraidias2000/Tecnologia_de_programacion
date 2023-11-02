/**
 * PosicionEstaDentroTablero.java
 * ccatalan (01/2018) 
 *   
 */

package control;

import modelo.TableroNEnRaya;
import modelo.Posicion;

/**
 *  Regla de posición está dentro del tablero
 * 
 */
class PosicionEstaDentroTablero implements ReglaJugada {
  /**
   *  Devuelve verdadero si se cumple la regla o falso en caso contrario
   * 
   */     
  public boolean seCumple(TableroNEnRaya tablero, Posicion posicion) {
    return tablero.posicionEstaDentro(posicion);
  }  
}
