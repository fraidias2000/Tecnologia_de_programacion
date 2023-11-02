/**
 * ReglaJugada.java
 * ccatalan (01/2018) 
 *   
 */

package control;

import modelo.TableroNEnRaya;
import modelo.Posicion;

/**
 *  Interfaz de las reglas de una jugada del juego
 * 
 */
interface ReglaJugada {
  /**
   *  Devuelve verdadero si se cumple la regla o falso en caso contrario
   * 
   */   
  public boolean seCumple(TableroNEnRaya tablero, Posicion posicion);    
}
