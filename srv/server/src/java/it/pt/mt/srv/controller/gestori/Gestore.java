/* file:        Gestore.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 */
package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenza un gestore generico.
 */
public interface Gestore {

    void setMittente(IConnessione connessione);

    void esegui() throws ConditionException;
}
