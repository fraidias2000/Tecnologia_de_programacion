/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package modelo;

/**
 *  Tupla genérica de tres objetos
 */
public class Sixtupla<A, B, C, D, E, F> {
    public final A a;
    public final B b;
    public final C c;
    public final D d;
    public final E e;
    public final F f;

    /**
     * Constructor de una trupla
     * 
     */   
    public Sixtupla(A a, B b, C c, D d, E e, F f) { 
        this.a = a; 
        this.b = b; 
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }
}
