/**
 * JuegoVista.java
 * ccatalan (02/2019) 
 *   
 */
package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.OyenteVista;
import control.OyenteVista.Evento;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import modelo.Posicion;
import modelo.Ficha;
import modelo.TableroNEnRaya;
import modelo.Tupla;

/**
 * Vista Swing del juego de tablero
 * 
 */
public class JuegoVista extends JFrame implements ActionListener, PropertyChangeListener {  
  private static JuegoVista instancia = null;  // es singleton
  private OyenteVista oyenteVista;
  private String version;
  private Tablero tableroVista;
  private TableroNEnRaya tableroModelo;
  private JLabel lineaEstado;
  private ImageIcon icono;
  private ImageIcon iconoCruz;
  private ImageIcon iconoCirculo;
  private JMenuItem menuFicheroGuardar;
  private JMenuItem menuFicheroGuardarComo;
  private JButton botonAbrir;
  private JButton botonGuardar;

  public static final int ABRIR_FICHERO = 0;
  public static final int GUARDAR_FICHERO = 1;
  public static final int OPCION_SI = JOptionPane.YES_OPTION;
  
  /** Identificadores de textos dependientes del idioma */          
  private static final String MENU_FICHERO = "Fichero";
  private static final String MENU_ITEM_NUEVA = "Nueva partida";
  private static final char ATAJO_MENU_ITEM_NUEVA = 'N';
  private static final String MENU_ITEM_ABRIR = "Abrir partida...";
  private static final char ATAJO_MENU_ITEM_ABRIR = 'A';
  private static final String MENU_ITEM_GUARDAR = "Guardar partida";
  private static final char ATAJO_MENU_ITEM_GUARDAR = 'G';
  private static final String MENU_ITEM_GUARDAR_COMO = 
          "Guardar partida como...";
  private static final char ATAJO_MENU_ITEM_GUARDAR_COMO = 'C';
  private static final String MENU_ITEM_SALIR = "Salir";
  private static final char ATAJO_MENU_ITEM_SALIR = 'S';
  private static final String MENU_AYUDA = "Ayuda";
  private static final String MENU_ITEM_ACERCA_DE = "Acerca de...";
  private static final char ATAJO_ITEM_ACERCA_DE = 'A';
  
  private static final String ESTADO_INICIAL = "Empieza nueva o abre partida";
  
  public static final String EXT_FICHERO_PARTIDA = ".ter";
  public static final String FILTRO_PARTIDAS = "Partidas";
  public static final String TURNO = "Turno de ";
  public static final String CONFIRMACION_GUARDAR = 
          "¿Quieres guardar la partida actual?";
  public static final String PARTIDA_FIN_TRES_EN_RAYA = "¡¡ Tres en raya !!";
  public static final String PARTIDA_FIN_EMPATE = "¡¡ Empate !!";
  public static final String PARTIDA_NO_GUARDADA = "No pudo guardarse partida";
  public static final String PARTIDA_NO_LEIDA = "No pudo leerse partida";
  public static final String FICHERO_NO_ENCONTRADO = "Fichero no encontrado";
 
  /* ficheros de recursos */
  private static final String RUTA_RECURSOS = "/vista/recursos/";
  private static final String ICONO_APLICACION = "juego.png";
  private static final String ICONO_CRUZ = "cruz.png";
  private static final String ICONO_CIRCULO = "circulo.png";     
  private static final String ICONO_NUEVA = "nueva.png";     
  private static final String ICONO_ABRIR = "abrir.png";     
  private static final String ICONO_GUARDAR = "guardar.png";     
  
  /** Constantes para redimensionamiento */
  private static final int MARGEN_HORIZONTAL = 50;
  private static final int MARGEN_VERTICAL = 100;
  
  /**
   * Construye la vista del tablero de filas x columnas con el oyente para
   * eventos de la interfaz de usuario indicado
   * 
   */
  private JuegoVista(OyenteVista oyenteVista, String version, 
                     int filas, int columnas) { 
    super(version);

    this.oyenteVista = oyenteVista;
    this.version = version; 
    
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
      }
    });
    
    setLayout(new BorderLayout());
    
    JPanel panelNorte = new JPanel();
    panelNorte.setLayout(new GridLayout(2, 1));
    
    // creamos elementos
    creaMenus(panelNorte);
    creaBarraHerramientas(panelNorte);
    add(panelNorte, BorderLayout.NORTH);
    
    JPanel panelCentral = new JPanel();
    //panelCentral.setLayout(new FlowLayout());
    creaTablero(panelCentral, filas, columnas);
    add(panelCentral, BorderLayout.CENTER);
    
    lineaEstado = new JLabel(ESTADO_INICIAL);
    add(lineaEstado, BorderLayout.SOUTH);
 
    icono = new ImageIcon(
            this.getClass().getResource(RUTA_RECURSOS + ICONO_APLICACION));
    setIconImage(icono.getImage());
    
    iconoCruz = new ImageIcon(
           this.getClass().getResource(RUTA_RECURSOS + ICONO_CRUZ));
    
    iconoCirculo = new ImageIcon(
           this.getClass().getResource(RUTA_RECURSOS + ICONO_CIRCULO));
    
    // hacemos visible con el tamaño y la posición deseados     
    setResizable(false);
    setSize((int)(tableroVista.dimensionCasilla().getWidth() * 
                  columnas + MARGEN_HORIZONTAL), 
            (int)(tableroVista.dimensionCasilla().getHeight() * 
                  filas + MARGEN_VERTICAL));
        
    pack();  // ajusta ventana y sus componentes
    setLocationRelativeTo(null);  // centra en la pantalla    
    setVisible(true);
  }  
  
  /**
   * Devuelve la instancia de la vista del tablero
   * 
   */        
  public static synchronized JuegoVista devolverInstancia(
          OyenteVista oyenteIU, String version, int filas, int columnas) {
    if (instancia == null)
      instancia = new JuegoVista(oyenteIU, version, filas, columnas);    
    return instancia;
  } 
  
  /**
   * Crea los menus
   * 
   */  
  private void creaMenus(JPanel panelNorte) {
    JMenu menuFichero = new JMenu(MENU_FICHERO);

    JMenuItem menuFicheroNueva = 
      new JMenuItem(MENU_ITEM_NUEVA, ATAJO_MENU_ITEM_NUEVA);
    menuFicheroNueva.addActionListener(this);
    menuFicheroNueva.setActionCommand(MENU_ITEM_NUEVA);
    menuFichero.add(menuFicheroNueva);
    
    JMenuItem menuFicheroAbrir = 
      new JMenuItem(MENU_ITEM_ABRIR, ATAJO_MENU_ITEM_ABRIR);
    menuFicheroAbrir.addActionListener(this);
    menuFicheroAbrir.setActionCommand(MENU_ITEM_ABRIR);
    menuFichero.add(menuFicheroAbrir);

    menuFichero.addSeparator(); 
    
    menuFicheroGuardar = 
      new JMenuItem(MENU_ITEM_GUARDAR, ATAJO_MENU_ITEM_GUARDAR);
    menuFicheroGuardar.setEnabled(false);
    menuFicheroGuardar.addActionListener(this);
    menuFicheroGuardar.setActionCommand(MENU_ITEM_GUARDAR);
    menuFichero.add(menuFicheroGuardar);   
    
    menuFicheroGuardarComo = 
      new JMenuItem(MENU_ITEM_GUARDAR_COMO, ATAJO_MENU_ITEM_GUARDAR_COMO);
    menuFicheroGuardarComo.setEnabled(false);
    menuFicheroGuardarComo.addActionListener(this);
    menuFicheroGuardarComo.setActionCommand(MENU_ITEM_GUARDAR_COMO);
    menuFichero.add(menuFicheroGuardarComo);    
    
    menuFichero.addSeparator();
    
    JMenuItem menuFicheroSalir = 
      new JMenuItem(MENU_ITEM_SALIR, ATAJO_MENU_ITEM_SALIR);
    menuFicheroSalir.addActionListener(this);
    menuFicheroSalir.setActionCommand(MENU_ITEM_SALIR);
    menuFichero.add(menuFicheroSalir);    
    
    JMenu menuAyuda = new JMenu(MENU_AYUDA);
    
    JMenuItem menuAyudaAcercaDe = 
      new JMenuItem(MENU_ITEM_ACERCA_DE, ATAJO_ITEM_ACERCA_DE);
    menuAyudaAcercaDe.addActionListener(this);
    menuAyudaAcercaDe.setActionCommand(MENU_ITEM_ACERCA_DE);
    menuAyuda.add(menuAyudaAcercaDe);    

    JMenuBar menuPrincipal = new JMenuBar();
    menuPrincipal.add(menuFichero);
    menuPrincipal.add(menuAyuda);
 
    panelNorte.add(menuPrincipal);
  }

  /**
   * Crea barra de herramientas
   * 
   */ 
  private void creaBarraHerramientas(JPanel panelNorte) {
    JToolBar barra = new JToolBar();
    barra.setFloatable(false);

    JButton botonNueva = new JButton(new ImageIcon(
       this.getClass().getResource(RUTA_RECURSOS + ICONO_NUEVA)));
    botonNueva.setToolTipText(MENU_ITEM_NUEVA);
    botonNueva.addActionListener(this);
    botonNueva.setActionCommand(MENU_ITEM_NUEVA);
    barra.add(botonNueva);

    botonAbrir = new JButton(new ImageIcon(
       this.getClass().getResource(RUTA_RECURSOS + ICONO_ABRIR)));    
    botonAbrir.setToolTipText(MENU_ITEM_ABRIR);
    botonAbrir.addActionListener(this);
    botonAbrir.setActionCommand(MENU_ITEM_ABRIR);
    barra.add(botonAbrir);
    
    botonGuardar = new JButton(new ImageIcon(
       this.getClass().getResource(RUTA_RECURSOS + ICONO_GUARDAR)));    
    botonGuardar.setEnabled(false);
    botonGuardar.setToolTipText(MENU_ITEM_GUARDAR);
    botonGuardar.addActionListener(this);
    botonGuardar.setActionCommand(MENU_ITEM_GUARDAR);
    barra.add(botonGuardar);

    panelNorte.add(barra);
  }  
  
  /**
   * Crea la vista del tablero
   * 
   */   
  private void creaTablero(JPanel panel, int numFilTab, int numColTab) {
    tableroVista = new Tablero(this, numFilTab, numColTab, 
                                    Tablero.RECIBIR_EVENTOS_RATON);
    panel.add(tableroVista);
  }
 
  /**
   * Sobreescribe actionPerformed
   * 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch(e.getActionCommand()) {
        case MENU_ITEM_NUEVA:
           oyenteVista.eventoProducido(OyenteVista.Evento.NUEVA, null);
           tableroVista.habilitar(true);
           inicializarVista(); 
           break;

        case MENU_ITEM_ABRIR:           
           oyenteVista.eventoProducido(OyenteVista.Evento.ABRIR, null);
           break;           
           
        case MENU_ITEM_GUARDAR:
           oyenteVista.eventoProducido(OyenteVista.Evento.GUARDAR, null);
           break;           
             
        case MENU_ITEM_GUARDAR_COMO:
           oyenteVista.eventoProducido(OyenteVista.Evento.GUARDAR_COMO, null);
           break;             
           
        case MENU_ITEM_SALIR:
           oyenteVista.eventoProducido(OyenteVista.Evento.SALIR, null);
           break;
        
        case MENU_ITEM_ACERCA_DE:
          JOptionPane.showMessageDialog(this, version + "\n", 
             MENU_ITEM_ACERCA_DE, JOptionPane.INFORMATION_MESSAGE,  icono);   
          break;
    }
  }
  
  /**
   * Selecciona fichero de partida
   * 
   */  
  public String seleccionarFichero(int operacion) {
    String nombreFichero = null;
    int resultado = 0;
    
    JFileChooser dialogoSeleccionar = new JFileChooser(new File("."));
    FileNameExtensionFilter filtro = 
         new FileNameExtensionFilter(FILTRO_PARTIDAS, 
                                     EXT_FICHERO_PARTIDA.substring(1));
    
    dialogoSeleccionar.setFileFilter(filtro);
    
    if (operacion == ABRIR_FICHERO) {
      resultado = dialogoSeleccionar.showOpenDialog(this);
    } else {
      resultado = dialogoSeleccionar.showSaveDialog(this);
    }
    
    if(resultado == JFileChooser.APPROVE_OPTION) {
      nombreFichero = dialogoSeleccionar.getSelectedFile().getName();
      
      // pone extensión si hace falta al guardar
      if (! nombreFichero.endsWith(EXT_FICHERO_PARTIDA) && 
          (operacion == GUARDAR_FICHERO)) {
        nombreFichero = nombreFichero + EXT_FICHERO_PARTIDA;
      }
    
      ponerTitulo(nombreFichero);
    }
    
    return nombreFichero;
  }

  public void ponerTitulo(String nombreFichero) {
     if (nombreFichero.equals("")) {
       setTitle(version); 
     } else {
       setTitle(nombreFichero + " - " + version);          
     }
  }
  
  /**
   * Escribe mensaje en línea de estado
   * 
   */          
  public void mensajeLineaEstado(String mensaje) {
    lineaEstado.setText(mensaje);
  }
  
  /**
   * Escribe mensaje con diálogo modal
   * 
   */    
  public void mensajeDialogo(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje, version, 
        JOptionPane.INFORMATION_MESSAGE,  icono);    
  }  
  
  /**
   * Cuadro diálogo de confirmación acción
   * 
   */    
  public int mensajeConfirmacion(String mensaje) {
    return JOptionPane.showConfirmDialog(this, mensaje, version,
               JOptionPane.YES_NO_OPTION, 
               JOptionPane.INFORMATION_MESSAGE); 
  } 
  
  /**
   * Pone Icono del color indicado en la posición indicada
   * 
   */  
  public void ponerIconoCasilla(Posicion posicion, Ficha ficha) {
    if (ficha != Ficha.VACIA) {
      if (ficha == Ficha.CRUZ) {
         tableroVista.ponerIconoCasilla(posicion, iconoCruz);   
      } 
      else if (ficha == Ficha.CIRCULO) {
         tableroVista.ponerIconoCasilla(posicion, iconoCirculo);   
      }
      habilitarEvento(Evento.GUARDAR, true);
      habilitarEvento(Evento.GUARDAR_COMO, true);
    } else {
      tableroVista.ponerIconoCasilla(posicion, null);   
    } 
  } 

  /**
   * Inicializa vista
   * 
   */     
  public void inicializarVista() {
    tableroVista.inicializar();
  }
  
  /**
   * Recupera partida con el tablero indicado
   * 
   */   
  public void recuperarPartida(TableroNEnRaya tablero) {
     for (int fila = 0; fila < tablero.devuelveNumFilas(); fila++) {
       for (int columna = 0; columna < tablero.devuelveNumColumnas(); columna++) {
         Posicion posicion = new Posicion(fila, columna);
         ponerIconoCasilla(posicion, tablero.devolverFicha(posicion));   
       }
     } 
  }
  
  /**
   * Notificación de un evento de la interfaz de usuario
   * 
   */
  public void notificacion(OyenteVista.Evento evento, Object obj) {
    oyenteVista.eventoProducido(evento, obj);    
  }
 
  /**
   * Sobreescribe propertyChange para recibir eventos del tablero observado
   * 
   */  
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    Tupla tupla;
    
    if (evt.getPropertyName().equals(TableroNEnRaya.NOMBRE_PROPIEDAD_FICHA)) {
      tupla = (Tupla)evt.getNewValue();
      ponerIconoCasilla((Posicion)tupla.b, ((Ficha)tupla.a));   
    }
  }
  
  /**
   * Habilita o deshabilita evento vista
   * 
   */
  public void habilitarEvento(OyenteVista.Evento evento, boolean habilitacion) { 
    switch(evento) {
        case GUARDAR:
            menuFicheroGuardar.setEnabled(habilitacion);
            botonGuardar.setEnabled(habilitacion);              
            break;

        case GUARDAR_COMO:
            menuFicheroGuardarComo.setEnabled(habilitacion);            
            break;   
            
        case PONER_FICHA:
            tableroVista.habilitar(habilitacion);
            break;
    }
  }   
}
