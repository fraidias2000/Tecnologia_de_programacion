package javaapplication3;

// jugando con el teclado...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class TecladoSwing extends JFrame {
  public static final int ARRIBA = 1, ABAJO = 2, IZDA = 3, DCHA = 4;
  private JLabel[] etiquetas = new JLabel[9];
  private int x = 1, y = 1;
  private Icon icono = new ImageIcon("duke.gif");
  TecladoSwing(String titulo) {
    super(titulo);
    getContentPane().setLayout(new GridLayout(3,3));
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });  
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
		etiquetas[3 * x + y].setIcon(null);
        switch(e.getKeyCode()) {    
          case KeyEvent.VK_UP:   if(x > 0)  x--; break;
          case KeyEvent.VK_DOWN: if(x < 2 ) x++; break;
          case KeyEvent.VK_LEFT: if(y > 0)  y--; break;
          case KeyEvent.VK_RIGHT:if(y < 2)  y++; break;
        }
        etiquetas[3 * x + y].setIcon(icono);   
      } 
    });
    
    for(int i = 0; i < 9; i++) {
      etiquetas[i] = new JLabel(); getContentPane().add(etiquetas[i]);
      etiquetas[i].setHorizontalAlignment(SwingConstants.CENTER);
      etiquetas[i].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    }
    etiquetas[3*y+x].setIcon(icono);
    setBounds(250,200,350,200); setVisible(true);  
  } 
  
  public static void main (String[] args) {
    new TecladoSwing("Jugando con el teclado...");
  }
}
  

