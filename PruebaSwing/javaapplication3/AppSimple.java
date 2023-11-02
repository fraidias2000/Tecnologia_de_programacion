package javaapplication3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GestorBoton implements ActionListener {
  JButton b;
  GestorBoton(JButton b) { this.b = b; }
  public void actionPerformed(ActionEvent evt) {
    b.setText("No pulses");
  }  
}

public class AppSimple extends JFrame {
  AppSimple() {
    super("Push");
    getContentPane().setLayout(new BorderLayout());

    addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e){System.exit(0);}
    }); 
    JButton boton = new JButton("Pulsa");
	boton.addActionListener(new GestorBoton(boton));
    getContentPane().add(boton,BorderLayout.CENTER);
	setBounds(150,100,100,100); 
	setVisible(true);  
  } 
  public static void main (String[] args) {
      new AppSimple();
  }
}