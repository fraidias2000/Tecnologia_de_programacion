package javaapplication3;

// Animaci�n sencilla ...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnimacionSwing extends JFrame {
  private int i = 0, av = 1;
  Icon[] imagenes = { new ImageIcon("T1.gif"), new ImageIcon("T2.gif"),
       new ImageIcon("T3.gif"),new ImageIcon("T3.gif"),new ImageIcon("T5.gif"),
       new ImageIcon("T6.gif"),new ImageIcon("T7.gif"),new ImageIcon("T8.gif"),
       new ImageIcon("T9.gif"),new ImageIcon("T10.gif")};
  private JLabel imagen;  private Timer timer;
  AnimacionSwing(String titulo) {
    super(titulo);
    getContentPane().setLayout(new BorderLayout());
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) { System.exit(0); }
    });  
    
    timer = new Timer(50, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        i = (i + av); 
        if(i < 0) i = 9; 
        else if(i == imagenes.length) i = 0;
        imagen.setIcon(imagenes[i]);
      }
    });   
        
    imagen = new JLabel();
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(imagen,BorderLayout.CENTER);
    imagen.setIcon(imagenes[i]);
    
    Object[] sentido = {"adelante","atr�s"};
    JComboBox selSentido = new JComboBox(sentido);
    selSentido.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e){
        if(((String)e.getItem()).equals("adelante")) av = 1;
        else av = -1;
      }      
    });
    
    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evt) { System.exit(0); }
    });

    JScrollBar selPeriodo = new JScrollBar(JScrollBar.HORIZONTAL,50,0,50,5000);
    selPeriodo.setUnitIncrement(25);
    selPeriodo.addAdjustmentListener(new AdjustmentListener(){
      public void adjustmentValueChanged(AdjustmentEvent e) {
        timer.setDelay(e.getValue());
      }
    });
    
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());
    panel.add(botonSalir);  panel.add(selSentido);  panel.add(selPeriodo);
    
    getContentPane().add(panel,BorderLayout.SOUTH);
    
    timer.start();
    
    setBounds(250,200,440,250); setVisible(true);  
    setResizable(false);
  } 
  public static void main (String[] args) {
      new AnimacionSwing("Animaci�n sencilla...");
  }
}
  

