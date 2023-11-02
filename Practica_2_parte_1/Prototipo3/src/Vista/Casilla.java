/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *04/04/2020 
 * 
 */

package Vista;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import modelo.Posicion;

public class Casilla extends JLabel {
     private Posicion posicion;
     private int numero;

    public Casilla(Posicion posicion, int numero) {
        this.posicion = posicion;
        this.numero = numero;
        habilitar(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }
     
   /**
   * Habilita la casilla
   * 
   */
  public void habilitar(boolean habilitacion) {
    setEnabled(habilitacion);
  }
  
  /**
   * Devuelve la posición de la casilla
   * 
   */
  public Posicion devuelvePosicion() {
    return posicion;
  } 
  
   /**
   * Pone icono
   * 
   */    
  public void ponerIcono(Icon icono) {
    setIcon(icono);
  }
  
  /**
   * Sobreescribe toString
   * 
   */  
  @Override
  public String toString() {
    return posicion.toString();
  }
}
