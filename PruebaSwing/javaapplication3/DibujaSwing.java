package javaapplication3;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

class GestorRaton extends MouseAdapter {
  DibujaSwing ds;
  GestorRaton(DibujaSwing ds) { this.ds = ds; }
  public void mousePressed(MouseEvent e) {
    if(ds.inicio) { 
      ds.x = e.getX(); ds.y = e.getY();
      ds.inicio = false;
    } else {
        if(ds.botonRecta.isSelected())
          ds.tablaDibujo.figuras.add(new Recta(ds,e));
        else ds.tablaDibujo.figuras.add(new Circulo(ds,e));
        ds.tablaDibujo.repaint(); ds.inicio = true;
    }    
  }
}

class GestorSelFigura implements ActionListener {
  DibujaSwing ds;
  GestorSelFigura(DibujaSwing ds) { this.ds = ds; }
  public void actionPerformed(ActionEvent evt) {
    if(ds.botonRecta.isSelected()) {
      ds.botonRecta.setBorder(ds.activado);
      ds.botonCirculo.setBorder(ds.desactivado);
    }
    else {
      ds.botonCirculo.setBorder(ds.activado);
      ds.botonRecta.setBorder(ds.desactivado);
    }
  }
}

public class DibujaSwing extends JFrame {
  int x,y; boolean inicio = true; 
  TablaDibujo tablaDibujo;  
  String[] colores = {new Integer(Color.red.getRGB()).toString(),
    new Integer(Color.blue.getRGB()).toString(),
    new Integer(Color.green.getRGB()).toString()};
  String[] nomColores = {"rojo","azul","verde"};
  JComboBox selColor;
  JRadioButton botonRecta, botonCirculo;
  Border activado = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
  Border desactivado = BorderFactory.createBevelBorder(BevelBorder.RAISED);
  DibujaSwing(String titulo) {
    super(titulo);
		//setUndecorated(true);

    // maximizamos JFrame
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e){System.exit(0);}
    });  

    JToolBar barra = new JToolBar();    
    
    botonRecta = new JRadioButton(new ImageIcon("recta.gif"),true);
    botonRecta.setToolTipText("recta");
    botonRecta.addActionListener(new GestorSelFigura(this));
    botonRecta.setBorderPainted(true);
    botonRecta.setBorder(activado);
    barra.add(botonRecta);
    
    botonCirculo = new JRadioButton(new ImageIcon("circulo.gif"));
    botonCirculo.setToolTipText("c�rculo");
    botonCirculo.addActionListener(new GestorSelFigura(this));
    botonCirculo.setBorderPainted(true);
    botonCirculo.setBorder(desactivado);
    barra.add(botonCirculo);

    ButtonGroup selFigura = new ButtonGroup();
    selFigura.add(botonRecta);
    selFigura.add(botonCirculo);

    selColor = new JComboBox(nomColores);    
    barra.add(selColor);
    barra.add(new JToolBar.Separator());
    JButton botonSalir = new JButton("Salir");
    botonSalir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { System.exit(0); }
    });
    barra.add(botonSalir);        
    
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(barra,BorderLayout.NORTH);

    tablaDibujo = new TablaDibujo();
    getContentPane().add(tablaDibujo,BorderLayout.CENTER);   

    tablaDibujo.addMouseListener(new GestorRaton(this));  
    
    setBounds(100,120,550,350); setVisible(true);
  } 

  public static void main (String[] args) {
    new DibujaSwing("Dibujando ...");
  }
}

class TablaDibujo extends JPanel {
  java.util.List figuras = new ArrayList();
  
  TablaDibujo() {
    setBackground(Color.white);
  }
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Iterator ifig = figuras.iterator();
    while(ifig.hasNext()) ((Figura)ifig.next()).dibujar(g);
  }
}

abstract class Figura {
  int x,y;
  DibujaSwing ds;
  Color color;
  Figura(DibujaSwing ds) {
    this.ds = ds; x = ds.x; y = ds.y;
    color = Color.decode(ds.colores[ds.selColor.getSelectedIndex()]);
  }
  abstract void dibujar(Graphics g);
}

class Recta extends Figura {
  int xf,yf;
  String nombre = "recta";
  Recta(DibujaSwing ds, MouseEvent e) { 
    super(ds);
    this.xf = e.getX(); this.yf = e.getY();
  }
  void dibujar(Graphics g) {
    g.setColor(color);
    g.drawLine(x,y,xf,yf);
  }
}

class Circulo extends Figura {
  int xf,yf;
  String nombre = "c�rculo";
  Circulo(DibujaSwing ds, MouseEvent e) { 
    super(ds);
    this.xf = e.getX(); this.yf = e.getY();
  }
  void dibujar(Graphics g) {
    g.setColor(color);
    int d = Math.round((float)Math.sqrt(Math.pow(xf-x,2)+Math.pow(yf-y,2)));
    g.drawOval(Math.min(x,xf),Math.min(y,yf),d,d);
  }
}
