/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package Control;

/*
 *  Interfaz de oyente para recibir eventos de la interfaz de usuario
 */
public interface OyenteVista {
   public enum Evento {BUSCAR_VIAJES, VER_ASIENTOS, OCUPAR_ASIENTO, 
                        DESOCUPAR_ASIENTO, GENERAR_HOJA_VIAJE, SALIR }
  
   /**
    *  Llamado para notificar un evento de la interfaz de usuario
    */ 
   public void eventoProducido(Evento evento, Object obj)throws Exception;
    
}
