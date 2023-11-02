/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *04/04/2020 
 * 
 */

package Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import modelo.Oficina;
import modelo.Viaje;
import Control.OyenteVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.JOptionPane;
import modelo.Asiento;

public class OficinaVista implements ActionListener, PropertyChangeListener {
    /** Constantes para redimensionamiento */
  private static final int MARGEN_HORIZONTAL = 50;
  private static final int MARGEN_VERTICAL = 100;
  public static final int NUMERO_FILAS = 5;
  public static final int NUMERO_COLUMNAS = 5;
  private final String ASIGNAR = "ASIGNAR";
  private final String GENERAR = "GENERAR HOJA VIAJE";
  private final String BUSCAR = "BUSCAR VIAJES";
  private final String TEXTO = "VIAJE:";
  private final String VER_ASIENTOS = "VER ASIENTOS"; 
  private final int RELLENAR_ANIO = 1;
  private final int RELLENAR_MES = 2;
  private final int RELLENAR_DIA = 3;
  private final int ANYO_ACTUAL = 2020;
  private final int ENERO = 1;
  private final int FEBRERO = 2;
  private final int MARZO = 3;
  private final int ABRIL = 4;
  private final int MAYO = 5;
  private final int JUNIO = 6;
  private final int JULIO = 7;
  private final int AGOSTO = 8;
  private final int SEPTIEMBRE = 9;
  private final int OCTUBRE = 10;
  private final int NOVIEMBRE = 11;
  private final int DICIEMBRE = 12;
  private static final int[] anios = {2020,2021};
  private static final String[] meses = {"Enero", "Febrero", "Marzo", "Abril", 
      "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", 
      "Diciembre"};
  private static final int[] dias = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,
      17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
  
  private Casilla casillaSeleccionada;
  private static OficinaVista instancia = null;
  private OyenteVista oyenteVista;
  JComboBox seleccionarAnio ;
  JComboBox seleccionarMes;
  JComboBox seleccionarDia;
  JComboBox viajeSeleccionado;
  JTextArea texto;
  JButton verAsientos ;
  JButton buscarViaje;
  
  private Plano planoVista;
  private JFrame ventana;
  private Oficina oficina;
  private Viaje viajes[];

  public OficinaVista(OyenteVista oyenteVista, String ficheroViajes, String ficheroAutobuses) throws FileNotFoundException {       
    this.oyenteVista = oyenteVista;
    oficina = new Oficina(ficheroViajes, ficheroAutobuses);
    
    viajes = new Viaje[oficina.ViajesTotales()];
    viajes = oficina.rellenarVector(viajes);
    
    crearVentana();   
    }
  
     /**
   * Devuelve la instancia de la vista del tablero
   * 
   */        
  public static synchronized OficinaVista devolverInstancia(
                             OyenteVista oyenteVista,String ficheroViajes,
                             String ficheroAutobuses)
                             throws FileNotFoundException {
    if (instancia == null)
      instancia = new OficinaVista(oyenteVista,ficheroViajes, ficheroAutobuses);    
    return instancia;
  } 
  
  /*
  * Crea una ventana donde aparece toda la informacion de los viajes y la 
  * distribución de los asientos del autobús.
  */
  public void crearVentana(){
    ventana = new JFrame();
   
      ventana.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
      }
    });
     
    JPanel panelNorte = new JPanel();
    JPanel panelAsientos = new JPanel();
    JPanel panelSur = new JPanel();
    
    ventana.getContentPane().setLayout(new BorderLayout());
      
    
    panelNorte.setLayout(new GridLayout(2, 4));
    panelAsientos.setLayout(new FlowLayout());
    panelSur.setLayout(new FlowLayout());
    
    // creamos elementos
    creaMenus(panelNorte);
    ventana.getContentPane().add(panelNorte, BorderLayout.NORTH);
    
    creaPlano(panelAsientos);
    ventana.getContentPane().add(panelAsientos, BorderLayout.CENTER);
    
    crearBotonesSur(panelSur);
    ventana.getContentPane().add(panelSur, BorderLayout.SOUTH);
    
    ventana.setResizable(false);    
    ventana.pack();  // ajusta ventana y sus componentes
    ventana.setVisible(true);
    ventana.setLocationRelativeTo(null);
  }
  
  /*
   * Crea un menú donde selecionar el viaje
   */
  public void creaMenus(JPanel panelNorte) {
    seleccionarAnio = new JComboBox();
    seleccionarMes = new JComboBox();
    seleccionarDia = new JComboBox();
    viajeSeleccionado = new JComboBox();
    texto = new JTextArea(TEXTO);
    verAsientos = crearBotonBarraHerramientas(VER_ASIENTOS);
    buscarViaje = crearBotonBarraHerramientas(BUSCAR);
   
    //rellenarLista(seleccionarAnio,anios);
    rellenarLista(seleccionarMes,meses);
    //rellenarLista(seleccionarDia,dias);

    panelNorte.add(seleccionarAnio);
    panelNorte.add(seleccionarMes);
    panelNorte.add(seleccionarDia);
    panelNorte.add(buscarViaje);
      
    panelNorte.add(texto);
    panelNorte.add(viajeSeleccionado);
    panelNorte.add(verAsientos);
  }
    
  /*
   * Crea el panel sur
   */
  public void crearBotonesSur(JPanel panelSur){
    JButton asignar = crearBotonBarraHerramientas(ASIGNAR);
    JButton generar = crearBotonBarraHerramientas(GENERAR);
    panelSur.add(asignar);
    panelSur.add(generar);
  }
   /**
   * Crea botón barra de herramientas
   */ 
  private JButton crearBotonBarraHerramientas(String etiqueta) {
    JButton boton = new JButton(etiqueta);
    boton.addActionListener(this);
    boton.setActionCommand(etiqueta);
    return boton;
  }  
  
 /*
  * Crea un panel con la distribucion de los asientos
  */
  public void creaPlano(JPanel panel) {
       planoVista = new Plano(this, Plano.RECIBE_EVENTOS_RATON);
       panel.add(planoVista);
  }
     
  public void seleccionarCasilla(Casilla casilla) {
    // Quita selección anterior  
    if (casillaSeleccionada != null) {  
      casillaSeleccionada.deseleccionar();
      //ponerTextoRecordatorio("");
    }     
    casilla.seleccionar();            
    this.casillaSeleccionada = casilla;
  }  
  
   /**
   * Sobreescribe actionPerformed de ActionListener
   */
  public void actionPerformed(ActionEvent e)  {
        notificacionAControl(e.getActionCommand());      
  }  
  
  public void rellenarAutobus(){
      
  
  
  }

public void rellenarLista(JComboBox lista, Object vector[]){ 
      for (int i = 0; i < vector.length; i++) {
        lista.addItem(vector[i]);   
      }
   }


  
 /**
   * Recibe notificación un evento de la interfaz de usuario
   */
  public void notificacionAControl(String evento){
    switch(evento) {
    
     case BUSCAR:
          viajeSeleccionado.addItem(seleccionarAnio.getSelectedItem() + "/" 
          + seleccionarMes.getSelectedItem() + "/" + 
          seleccionarDia.getSelectedItem());
          break;  
          case VER_ASIENTOS :
            GregorianCalendar fecha;
            int dia;
            int mes;
            int anio;
            for (int i = 0; i < oficina.ViajesTotales(); i++){
                fecha = viajes[i].getFecha();
                dia = fecha.get(Calendar.DAY_OF_MONTH);
                mes = fecha.get(Calendar.MONTH);
                anio = fecha.get(Calendar.YEAR);
                
                if(seleccionarAnio.getSelectedItem().equals(anio) &&  seleccionarMes.getSelectedItem().equals(meses[mes]) && seleccionarDia.getSelectedItem().equals(dia)){
                       planoVista.iniciarPlano(viajes[i]);
                }
            }
        break;
    }
  }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}