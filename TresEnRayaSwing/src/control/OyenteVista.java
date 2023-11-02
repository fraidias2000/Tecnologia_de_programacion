/**
 * OyenteVista.java
 * ccatalan (01/2018)  
 */
package control;

/**
 *  Interfaz de oyente para recibir eventos de la interfaz de usuario
 * 
 */
public interface OyenteVista {
   public enum Evento { NUEVA, ABRIR, GUARDAR, GUARDAR_COMO, SALIR, 
                        PONER_FICHA }
  
   /**
    *  Llamado para notificar un evento de la interfaz de usuario
    * 
    */ 
   public void eventoProducido(Evento evento, Object obj);
}
