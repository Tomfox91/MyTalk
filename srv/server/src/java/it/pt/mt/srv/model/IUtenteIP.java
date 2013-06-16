/* file:        IUtenteIP.java
 * package:     it.pt.mt.srv.model
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 */
package it.pt.mt.srv.model;

/**
 * Interfaccia che rappresenta un generico utente connesso tramite ip.
 */
public interface IUtenteIP extends IUtente {

    String getIP();
}
