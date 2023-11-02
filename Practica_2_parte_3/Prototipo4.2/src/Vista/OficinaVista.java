/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
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
   private static final String MENSAJE_NO_ENCONTRADO = "No existe un viaje para tal fecha";
  private static final int MARGEN_HORIZONTAL = 50;
  private static final int MARGEN_VERTICAL = 100;
  public static final int NUMERO_FILAS = 5;
  public static final int NUMERO_COLUMNAS = 5;
  private final String ASIGNAR = "ASIGNAR";
  private final String GENERAR = "GENERAR HOJA VIAJE";
  private final String BUSCAR = "BUSCAR VIAJES";
  private final String TEXTO = "VIAJE:";
  private final String VER_ASIENTOS = "VER ASIENTOS"; 
 
  private static final Object [] anios = {2020, 2021, 2022, 2023, 2024, 2025};
  private static final Object[] meses = {"Enero", "Febrero", "Marzo", "Abril", 
      "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", 
      "Diciembre"};
  
  private AsientoVista casillaSeleccionada;
  private static OficinaVista instancia = null;
  private OyenteVista oyenteVista;
  JComboBox seleccionarAnio ;
  JComboBox seleccionarMes;
  JComboBox seleccionarDia;
  JComboBox viajeSeleccionado;
  JTextArea texto;
  JButton verAsientos ;
  JButton buscarViaje;
  
  private AutobusVista planoVista;
  private JFrame ventana;
  private Oficina oficina;
  private Viaje viajes[];

  public OficinaVista(OyenteVista oyenteVista, String ficheroViajes, String ficheroAutobuses) throws FileNotFoundException {       
    this.oyenteVista = oyenteVista;
    oficina = new Oficina(ficheroViajes, ficheroAutobuses);
    viajes = new Viaje[oficina.ViajesTotales()];
    viajes = oficina.obtenerViajes(viajes);
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
      } });
     
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
   
    rellenarComboBox(seleccionarAnio,anios);
    rellenarComboBox(seleccionarMes,meses);
    rellenarDias(seleccionarDia);

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
       planoVista = new AutobusVista(this, AutobusVista.RECIBE_EVENTOS_RATON);
       panel.add(planoVista);
  }
  
  
     /*
      * Selecciona un asiento
      */
  public void seleccionarAsiento(AsientoVista asiento) {
    // Quita selección anterior  
    if (casillaSeleccionada != null) {  
      casillaSeleccionada.deseleccionar();
    }     
    asiento.seleccionar();            
    this.casillaSeleccionada = asiento;
  }  
  
   /**
   * Sobreescribe actionPerformed de ActionListener
   */
  public void actionPerformed(ActionEvent e)  {
        notificacionAControl(e.getActionCommand());      
  }  
  
  /*
   * Rellena el JComboBox dias
   */
  public void rellenarDias(JComboBox lista){
      for (int i = 1; i <= 31; i++) {
          lista.addItem(i);   
      }
  }
  
  /*
   * Rellena un JcomboBox con un vector dado
   */
public void rellenarComboBox(JComboBox lista, Object vector[]){ 
      for (int i = 0; i < vector.length; i++) {
        lista.addItem(vector[i]);   
      }
   }
  
  /*
   * Recibe notificación un evento de la interfaz de usuario
   */
  public void notificacionAControl(String evento){
    switch(evento) {
     case BUSCAR:
          buscarAsiento();
          break;  
          case VER_ASIENTOS :
            verAsientos();
        break;
    }
  }
  /*
   * Busca los asientos para un viaje dado
   */
  
  public void buscarAsiento(){
      viajeSeleccionado.addItem(seleccionarAnio.getSelectedItem() + "/" 
          + seleccionarMes.getSelectedItem() + "/" + 
          seleccionarDia.getSelectedItem());
  }
  
  /*
   * Muestra los asientos para un viaje dado
   */
  public void verAsientos(){
      GregorianCalendar fecha;
            int dia;
            int mes;
            int anio;
            boolean encontrado = false;
            for (int i = 0; i < oficina.ViajesTotales() && !encontrado; i++){
                fecha = viajes[i].getFecha();
                dia = fecha.get(Calendar.DAY_OF_MONTH);
                mes = fecha.get(Calendar.MONTH);
                anio = fecha.get(Calendar.YEAR);
                if((seleccionarAnio.getSelectedItem().equals(anio)) &&  
                   (seleccionarMes.getSelectedItem().equals(meses[mes-1])) &&
                   (seleccionarDia.getSelectedItem().equals(dia))){               
                    planoVista.iniciarVistaPlano(viajes[i]);
                    encontrado = true;
                }
            }
            if(!encontrado){
                JOptionPane.showMessageDialog(null, MENSAJE_NO_ENCONTRADO, 
                null, JOptionPane.INFORMATION_MESSAGE);
            }
  
  }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}