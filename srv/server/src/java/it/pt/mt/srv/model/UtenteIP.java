/* file:        UtenteIP.java
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

import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta un utente
 */
public class UtenteIP implements IUtenteIP {

    private String IP;

    /**
     * Crea l'oggetto a partire dall'ip indicato.
     *
     * @param IP l'indirizzo ip dell'utente
     * @throws ConditionException se l'indirizzo ip non è correto
     */
    public UtenteIP(String IP) throws ConditionException {
        checkArg(IP.matches("^[0-9a-fA-F.:]{7,50}$"));

        this.IP = IP;
    }

    /**
     * Restituisce il nome dell'utente indicato.
     *
     * @return la stringa contenente l'ip dell'utente
     */
    @Override
    public String getNome() {
        return getIP();
    }

    /**
     * Restituisce ip dell'utente indicato.
     *
     * @return la stringa contenente l'ip dell'utente
     */
    @Override
    public String getIP() {
        return IP;
    }
}
