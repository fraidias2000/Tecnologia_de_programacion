/**
 * Casilla.java
 * ccatalan (02/2019) 
 *   
 */
package vista;

import control.OyenteVista;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import modelo.Posicion;

/**
 * Vista de una casilla del tablero a partir de un JLabel
 * 
 */
class Casilla extends JLabel {
  private Posicion posicion;
  private JuegoVista juegoVista;

  /**
   * Construye una vista con el icono indicado de la casilla en una posición 
   * 
   */
  Casilla(JuegoVista juegoVista, Posicion posicion, Icon icono,
               boolean recibeEventosRaton) {
    this.posicion = posicion;
    this.juegoVista = juegoVista;
    setIcon(icono);
        
    setEnabled(false);
    
    setHorizontalAlignment(SwingConstants.CENTER);
    //setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    
    if (recibeEventosRaton) {
      recibirEventosRaton();
    }
  }
  
  /**
   * Construye una vista sin icono de la casilla en una posición 
   * 
   */
  Casilla(JuegoVista vista, Posicion posicion, 
               boolean recibeEventosRaton) {
    this(vista, posicion, null, recibeEventosRaton);
  }

  /**
   * Recibe los eventos de ratón
   * 
   */  
  private void recibirEventosRaton() {
    addMouseListener(new MouseAdapter() { 
      @Override
      public void mousePressed(MouseEvent e) { 
        if (isEnabled()) {          
          juegoVista.notificacion(OyenteVista.Evento.PONER_FICHA, posicion);         
        }        
      } 
    });    
  }
  
  /**
   * Habilita la casilla
   * 
   */
  void habilitar(boolean habilitacion) {
    setEnabled(habilitacion);
  }
  
  /**
   * Devuelve la posición de la casilla
   * 
   */
  Posicion devuelvePosicion() {
    return posicion;
  }
    
  /**
   * Pone icono
   * 
   */    
  void ponerIcono(Icon icono) {
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
      
      