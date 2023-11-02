/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
 * 
 */
package Control;

/**
 *
 * @author Usuario
 */
public interface OyenteVista {
   public enum Evento { OCUPAR_ASIENTO, DESOCUPAR_ASIENTO, SALIR }
  
   /**
    *  Llamado para notificar un evento de la interfaz de usuario
    */ 
   public void eventoProducido(Evento evento, Object obj);
    
}
