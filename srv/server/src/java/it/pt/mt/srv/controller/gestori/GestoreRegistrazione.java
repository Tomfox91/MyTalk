/* file:        GestoreRegistrazione.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-16  TF  Prima stesura
 */
package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

public class GestoreRegistrazione
        extends AbstractGestore implements GestoreIp {

    private String email;
    private String username;
    private String password;
    private String domanda;
    private String risposta;

    /**
     * Imposta l'email.
     *
     * @param email l'email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta lo username.
     *
     * @param username lo username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Imposta la password.
     *
     * @param password la password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Imposta la domanda di sicurezza.
     *
     * @param domanda la domanda di sicurezza da impostare
     */
    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    /**
     * Imposta la risposta alla domanda di sicurezza.
     *
     * @param risposta la risposta alla domanda segreta da impostare
     */
    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    /**
     * Registra il nuovo utente e invia una notifica di confermata
     * registrazione.
     *
     * @throws ConditionException se uno dei campi è nullo: email,
     * username, password, domanda o risposta.
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(email != null);
        checkArg(username != null);
        checkArg(password != null);
        checkArg(domanda != null);
        checkArg(risposta != null);

        getGestoreUtenti()
                .nuovoUtente(email, username, password, domanda, risposta);
        inviaNotificaConferma("Registrazione");
    }
}
