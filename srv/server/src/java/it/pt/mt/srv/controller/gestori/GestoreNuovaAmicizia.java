/* file:        GestoreNuovaAmicizia.java
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

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.NotificaNuovaRichiestaAmicizia;
import it.pt.mt.srv.model.IUtenteRegistrato;
import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta il gestore per l'EventoNuovaAmicizia.
 */
public class GestoreNuovaAmicizia
        extends AbstractGestore implements GestoreRegistrato {

    private String email;

    /**
     * Imposta l'email.
     *
     * @param email l'email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Riceve e inoltra la richiesta di amicizia all'indirizzo email.
     *
     * @throws ConditionException se il campo email è nulla
     */
    @Override
    public void esegui() throws ConditionException {
        checkArg(email != null);

        getGestoreUtenti().getUtenteRegistrato(email)
                .registraRichiestaAmiciziaDa(getConnessioneMittente().getEmail());

        inviaNotificaConferma("NuovaAmicizia");

        try {
            IUtenteRegistrato u = getUtenteRegistrato();
            NotificaNuovaRichiestaAmicizia n =
                    getFactotyNotifiche().creaNotificaNuovaRichiestaAmicizia();
            n.setEmail(u.getEmail());
            n.setUsername(u.getUserName());

            IConnessione c = getRegistroConnessioni().getConnessione(email);
            checkArg(c != null);
            c.send(n.serializza());
        } catch (ConditionException ex) {
            //ignoro
        }
    }
}
