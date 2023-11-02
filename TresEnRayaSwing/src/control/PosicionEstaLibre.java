/**
 * PosicionEstaLibre.java
 * ccatalan (01/2018) 
 *   
 */

package control;

import modelo.TableroNEnRaya;
import modelo.Posicion;

import modelo.Ficha;

/**
 *  Regla de posición del tablero está libre
 * 
 */
class PosicionEstaLibre implements ReglaJugada {
  /**
   *  Devuelve verdadero si se cumple la regla o falso en caso contrario
   * 
   */    
  public boolean seCumple(TableroNEnRaya tablero, Posicion posicion) {
    return (tablero.devolverFicha(posicion) == Ficha.VACIA); 
  }      
}
