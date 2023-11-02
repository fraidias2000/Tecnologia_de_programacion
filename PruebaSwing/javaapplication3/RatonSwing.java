package javaapplication3;

// Jugando con el rat�n ...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class OyenteVentana extends WindowAdapter {
  public void windowClosing(WindowEvent e) { System.exit(0); }
}
class OyenteRaton extends MouseAdapter {
  RatonSwing rs;
  OyenteRaton(RatonSwing rs) { this.rs = rs; }
  public void mousePressed(MouseEvent e) {
    rs.x(e.getX()); rs.y(e.getY()); rs.repaint();
  }
}
public class RatonSwing extends JFrame {
  private int x,y;
  RatonSwing(String titulo) {
    super(titulo);
    addWindowListener(new OyenteVentana());
    addMouseListener(new OyenteRaton(this));
    setBounds(150,100,350,200); setVisible(true);  
  } 
  public void x(int x) {this.x = x;}
  public void y(int y) {this.y = y;} 
  public void paint (Graphics g){ g.drawString(x+", "+y, x, y); }
  public static void main (String[] args) {
    new RatonSwing("Jugando con el rat�n...");
  }
}
