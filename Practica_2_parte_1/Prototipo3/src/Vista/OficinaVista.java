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

public class OficinaVista extends JFrame {
    /** Constantes para redimensionamiento */
  private static final int MARGEN_HORIZONTAL = 50;
  private static final int MARGEN_VERTICAL = 100;
  public static final int NUMERO_FILAS = 5;
  public static final int NUMERO_COLUMNAS = 10;
  private final String ASIGNAR = "ASIGNAR";
  private final String GENERAR = "GENERAR HOJA VIAJE";
  private final String BUSCAR = "BUSCAR VIAJES";
  private final String TEXTO = "VIAJE:";
  private final String VER_ASIENTOS = "VER ASIENTOS"; 
  private final int RELLENAR_ANIO = 1;
  private final int RELLENAR_MES = 2;
  private final int RELLENAR_DIA = 3;
  private final int ANYO_ACTUAL = 2020;
  
  private static OficinaVista instancia = null;
  private Map <Integer, Viaje> viajes;
  
  
  private Plano planoVista;
  private JFrame ventana;

  public OficinaVista(int filas, int columnas, Map <Integer, Viaje> viajes) {       
    this.viajes = viajes;
    crearVentana(); 
    setResizable(false); //El usuario no puede cambiar el tamaño del marco
    setSize((int)(planoVista.dimensionCasilla().getWidth() * 
             columnas + MARGEN_HORIZONTAL), 
             (int)(planoVista.dimensionCasilla().getHeight() * 
             filas + MARGEN_VERTICAL));  
    }
  
     /**
   * Devuelve la instancia de la vista del tablero
   * 
   */        
  public static synchronized OficinaVista devolverInstancia(
                             int filas, int columnas, Map <Integer, Viaje> viajes) {
    if (instancia == null)
      instancia = new OficinaVista(filas, columnas, viajes);    
    return instancia;
  } 
  
  /*
  * Crea una ventana donde aparece toda la informacion de los viajes y la 
  * distribución de los asientos del autobús.
  */
  public void crearVentana(){
    ventana = new JFrame();
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
    
    crearBotones(panelSur);
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
    JComboBox seleccionarAnio = new JComboBox();
    JComboBox seleccionarMes = new JComboBox();
    JComboBox seleccionarDia = new JComboBox();
    
    rellenarLista(seleccionarAnio,RELLENAR_ANIO);
    rellenarLista(seleccionarMes,RELLENAR_MES);
    rellenarLista(seleccionarDia,RELLENAR_DIA);
    
    JButton buscarViaje = new JButton(BUSCAR);
    JComboBox viajeSeleccionado = new JComboBox();
    JButton verAsientos = new JButton(VER_ASIENTOS);
    JTextArea texto = new JTextArea(TEXTO);
    
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
  public void crearBotones(JPanel panelSur){
    JButton asignar = new JButton(ASIGNAR);
    JButton generar = new JButton(GENERAR);
    panelSur.add(asignar);
    panelSur.add(generar);
  }
  
 /*
  * Crea un panel con la distribucion de los asientos
  */
  public void creaPlano(JPanel panel) {
       planoVista = new Plano(this);
       panel.add(planoVista);
  }


  public void rellenarLista(JComboBox lista, int seleccion){
      int i = 0;

      switch(seleccion){
          case RELLENAR_ANIO:
               while(i <= viajes.size()){
                 if(viajes.get(i).getFecha().YEAR >= ANYO_ACTUAL){
                    lista.addItem(viajes.get(i).getFecha().YEAR); 
                    i++;
                 } 
              }
              break;
              
          case RELLENAR_MES:
              while(i <= viajes.size()){
                lista.addItem(viajes.get(i).getFecha().MONTH); 
                i++;
              }
              break;
             
           case RELLENAR_DIA:
               while(i <= viajes.size()){
                 lista.addItem(viajes.get(i).getFecha().DAY_OF_MONTH); 
                 i++;                
              }
              break;    
      } 
  }
}