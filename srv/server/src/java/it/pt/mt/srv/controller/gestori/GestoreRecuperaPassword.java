/* file:        GestoreRecuperaPassword.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-17  TF  Prima stesura
 */
package it.pt.mt.srv.controller.gestori;

import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta il gestore per l'EventoRecuperaPassword.
 */
public class GestoreRecuperaPassword
        extends AbstractGestore implements GestoreIp {

    private String risposta;
    private String nuova;
    private String email;

    /**
     * Imposta la risposta alla domanda di sicurezza.
     *
     * @param risposta la risposta alla domanda di sicurezza
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     * Imposta la nuova password.
     *
     * @param nuova la nuova password
     */
    public void setNuova(String nuova) {
        this.nuova = nuova;
    }

    /**
     * Imposta l'email.
     *
     * @param email l'email da impostare
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta la nuova password e invia una notifica di conferma.
     *
     * @throws ConditionException se uno dei campi risposta, nuova o
     * email è nullo oppure la risposta non è corretta
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(risposta != null);
        checkArg(nuova != null);
        checkArg(email != null);

        boolean ok = getGestoreUtenti().getUtenteRegistrato(email)
                .cambiaPasswordConRisposta(risposta, nuova);

        checkArg(ok);
        inviaNotificaConferma("RecuperaPassword");
    }
}
