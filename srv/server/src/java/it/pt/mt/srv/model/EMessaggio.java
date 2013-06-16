/* file:        EMessaggio.java
 * package:     it.pt.mt.srv.model
 * progetto:    server
 * versione:    1.1
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 * 1.1  2013-04-08  TF  Rinomino classe
 */
package it.pt.mt.srv.model;

/**
 * Rappresenta un messaggio testuale lasciato per un utente.
 */
public class EMessaggio implements IMessaggio {

    private String mittente;
    private String messaggio;

    /**
     * Restituisce il mittente del messaggio.
     *
     * @return l'email del mittente
     */
    @Override
    public String getMittente() {
        return mittente;
    }

    /**
     * Imposta il mittente del messaggio.
     *
     * @param mittente l'email del mittente del messaggio
     */
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    /**
     * Restituisce il testo del messaggio.
     *
     * @return la stringa che contiene il messaggio
     */
    @Override
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il testo del messaggio.
     *
     * @param messaggio il testo del messaggio
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}
