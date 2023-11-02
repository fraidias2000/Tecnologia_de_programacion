package javaapplication3;

// Tablas y ficheros ...
import java.io.*;
import java.util.*;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;

public class TablaSwing extends JFrame implements ActionListener {
  private static String ABRIR = "Abrir", SALIR = "Salir", ACERCA_DE = "Acerca de...";
  private JTable tabla;
  private JPanel panelTabla;
  private JScrollPane scrollTabla;
  private String titulo;
  TablaSwing(String titulo) {
    super(titulo);
    this.titulo = titulo;
    getContentPane().setLayout(new BorderLayout());
    addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e){System.exit(0);}
    });  
    
    JMenu menuFichero = new JMenu("Fichero");
    menuFichero.setMnemonic('F');
    JMenuItem menuFicheroAbrir = new JMenuItem(ABRIR,'A');
    menuFicheroAbrir.addActionListener(this);
    menuFichero.add(menuFicheroAbrir);
    JMenuItem menuFicheroSalir = new JMenuItem(SALIR,'S');    
    menuFicheroSalir.addActionListener(this);
    menuFichero.add(menuFicheroSalir);

    JMenu menuAyuda = new JMenu("Ayuda");
    menuAyuda.setMnemonic('A');
    JMenuItem menuAyudaAcercaDe = 
      new JMenuItem(ACERCA_DE,'A');
    menuAyudaAcercaDe.addActionListener(this);
    menuAyuda.add(menuAyudaAcercaDe);    

    JMenuBar barraMenu = new JMenuBar();
    barraMenu.add(menuFichero); barraMenu.add(menuAyuda);
    getContentPane().add(barraMenu,BorderLayout.NORTH);
    
    panelTabla = new JPanel();
    panelTabla.setLayout(new FlowLayout());
    getContentPane().add(panelTabla,BorderLayout.CENTER);
    
    setBounds(150,100,400,300); setVisible(true);  
  } 

  public void actionPerformed(ActionEvent evt) {
	JMenuItem source = (JMenuItem)evt.getSource();
    if(source.getText().equals(ABRIR)) abrir();
    else if(source.getText().equals(SALIR)) System.exit(0);
    else if(source.getText().equals(ACERCA_DE)) acercaDe();   
  }
  
  void abrir() {
    JFileChooser selFichero = new JFileChooser(new File(".")); 
    FileNameExtensionFilter filter = 
      new FileNameExtensionFilter("Datos tabla", "dat");
    selFichero.setFileFilter(filter);
    int returnVal = selFichero.showOpenDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      DatosTabla datosTabla = new DatosTabla(selFichero.getSelectedFile());
      if(datosTabla.ok()) {
        setTitle(titulo + "[" + selFichero.getSelectedFile().getName() + "]");
        if(scrollTabla != null) panelTabla.remove(scrollTabla);
        tabla = new JTable(datosTabla);
        tabla.setColumnSelectionAllowed(false);
        tabla.setRowSelectionAllowed(false);
        scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(350,200));
        panelTabla.add(scrollTabla);
        panelTabla.revalidate();
      }
    }
    repaint();
  }  
  
  void acercaDe() {
     JOptionPane.showMessageDialog(
        this, titulo + "\nAutor: Carlos Catal�n",
        ACERCA_DE, JOptionPane.INFORMATION_MESSAGE,
        new ImageIcon("duke.gif"));  
  }
  
  public static void main (String[] args) {
      new TablaSwing("Tablas y ficheros...");
  }
}

class DatosTabla extends AbstractTableModel {
  java.util.List<String> columnas = new ArrayList<String>(), fila;
  java.util.List filas = new ArrayList(); 
  
  boolean ficheroOk = false;
  DatosTabla(File f){
    String linea;
    try {
      BufferedReader fD = new BufferedReader(new FileReader(f));
      linea = fD.readLine();
      StringTokenizer st = new StringTokenizer(linea);
      while (st.hasMoreTokens()) columnas.add(st.nextToken());    
   
      while ((linea = fD.readLine()) != null) {
	    fila = new ArrayList<String>();
	    st = new StringTokenizer(linea);
	    while(st.hasMoreTokens()) fila.add(st.nextToken());
	    filas.add(fila);	    
	  }
      fD.close();      
      ficheroOk = true;
    }
    catch(Exception e) {
      JOptionPane.showMessageDialog(
        null, "Problemas en la lectura del fichero " + f.getName(),
        "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  boolean ok() { return ficheroOk; }
  public int getRowCount() { return filas.size(); }
  public int getColumnCount() { return columnas.size(); }
  public Object getValueAt(int fila, int col) {
    return ((java.util.List)filas.get(fila)).get(col);
  }
  public String getColumnName(int col) { return (String)columnas.get(col); }  
}

