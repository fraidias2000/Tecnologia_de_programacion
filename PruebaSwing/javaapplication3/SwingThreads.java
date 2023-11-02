package javaapplication3;

// Swing & Threads ...
import java.awt.*; 
import java.awt.event.*; 
import java.util.*; 
import javax.swing.*;
import javax.swing.event.*; 

class SwingThreads extends JFrame implements ChangeListener { 
  private DefaultListModel modeloLista; 
  Tarea tarea; int pausa = 10;
  public SwingThreads(String titulo) {
	super(titulo);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    modeloLista = new DefaultListModel(); 
    JList lista = new JList(modeloLista); 
    JScrollPane scrollPane = new JScrollPane(lista); 
    JPanel panel = new JPanel(); 
    panel.add(scrollPane); 
    getContentPane().add(panel, BorderLayout.CENTER);
    JButton boton = new JButton("Rellenar lista"); 
    boton.addActionListener(new ActionListener() { 
    public void actionPerformed( 
      ActionEvent event) { 
        tarea = new Tarea(modeloLista, pausa); tarea.start();
      } 
    }); 
    panel = new JPanel(); panel.add(boton); 
    SpinnerModel modeloSpinner = new SpinnerNumberModel(10, 1, 1000, 1);
    JSpinner spinner = new JSpinner(modeloSpinner);
    spinner.addChangeListener(this);
    panel.add(spinner);
    getContentPane().add(panel, BorderLayout.NORTH); 
    setSize(400,300); setVisible(true);
  } 
  public void stateChanged(ChangeEvent e) {
    JSpinner spinner = (JSpinner)(e.getSource());
    tarea.pausa(((Integer)spinner.getValue()).intValue());
  }
  public static void main(String[] args) { 
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new SwingThreads("Swing & threads"); 
      }
    });
  } 
} 
class Tarea extends Thread { 
  private DefaultListModel modelo; 
  private Random random; 
  int pausa;    
  public Tarea(DefaultListModel modelo, int pausa) { 
    this.modelo = modelo;  random = new Random(); this.pausa = pausa; 
  } 
  void pausa(int pausa) { this.pausa = pausa; }
  public void run() { 
    while (true) { 
      SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		  int i = random.nextInt(10);
          if (modelo.contains(i)) modelo.removeElement(i); 
          else modelo.addElement(i); 
	    }
	  });  
      try { Thread.sleep(pausa); } catch(Exception e) {}
    } 
  } 
} 
