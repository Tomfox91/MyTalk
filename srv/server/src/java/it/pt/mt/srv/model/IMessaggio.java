/* file:        IMessaggio.java
 * package:     it.pt.mt.srv.model
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 */
package it.pt.mt.srv.model;

/**
 * Interfaccia che rappresenta un messaggio.
 */
public interface IMessaggio {

    String getMittente();

    String getMessaggio();
}
