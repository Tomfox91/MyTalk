/* file:        ConditionException.java
 * package:     it.pt.mt.srv.util
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-12  TF  Prima stesura
 */
package it.pt.mt.srv.util;

/**
 * Rappresenta un eccezione e viene lanciata ogni volta che una condizione non è
 * rispettata.
 */
public class ConditionException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConditionException() {
    }

    /*public ConditionException(String message) {
     super(message);
     }

     public ConditionException(String message, Throwable cause) {
     super(message, cause);
     }*/
    public ConditionException(Throwable cause) {
        super(cause);
    }
}
