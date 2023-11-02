/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4
 *04/04/2020 
 * 
 */

package Vista;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import modelo.Posicion;

public class Casilla extends JLabel {
    private Posicion posicion;
    private OficinaVista oficina;
    private int numero;
    private boolean ocupado = false;
     
    private static final Color COLOR_SELECCIONADO = Color.YELLOW;
    private Color colorNoSeleccionado;
    private boolean seleccionado = false;
    private static final Color COLOR_OCUPADO = Color.CYAN;

    public Casilla(OficinaVista oficina, Posicion posicion, int numero, boolean recibeEventosRaton) {
        this.posicion = posicion;
        this.oficina = oficina;
        this.numero = numero;
        habilitar(true);
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        
        if (recibeEventosRaton) {
            recibirEventosRaton();
        }  
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
   * Pone numero de asiento
   * 
   */    
  public void ponerNumero(int numero) {
    setText(String.valueOf(numero));
  }
  
  /**
   * Recibe los eventos de ratón
   */  
  private void recibirEventosRaton() {
    addMouseListener(new MouseAdapter() { 
      @Override
      public void mousePressed(MouseEvent e) { 
        Casilla casilla = (Casilla)e.getSource();                 
          oficina.seleccionarCasilla(casilla);                 
        
      }
    });    
  }
  
    /**
   * Selecciona asiento
   */
  public void seleccionar() {
    seleccionado = true;
    setBackground(COLOR_SELECCIONADO);
  }  
  
  /**
   * Quita selección asiento
   */
  public void deseleccionar() {
    seleccionado = false;
    if (! ocupado) {
      setBackground(colorNoSeleccionado);
    } else {
      setBackground(COLOR_OCUPADO);  
    }
  }  

  /**
   * Ocupa un asiento
   */
  public void ocuparAsiento() {
    ocupado = true;
    setBackground(COLOR_OCUPADO);
  }  
  
  /**
   * Desocupa un asiento
   */
  public void desocuparAsiento() {
    ocupado = false;
    setBackground(colorNoSeleccionado);    
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
