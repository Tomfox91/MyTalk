/* file:        GestoreAccettazioneAmicizia.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.1
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-04-28  TF  Prima stesura
 * 1.1  2013-05-13  TF  Aggiungo possibilità di rifiuto di un'amicizia pendente
 */
package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.NotificaListaAggiungi;
import it.pt.mt.srv.model.IUtenteRegistrato;
import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta il gestore per l'EventoAccettazioneAmicizia.
 */
public class GestoreAccettazioneAmicizia
        extends AbstractGestore implements GestoreRegistrato {

    private String email;
    private boolean accettata = true;

    /**
     * Imposta l'email.
     *
     * @param email l'email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta la scelta dell'utente di accettare o rifiutare l'amicizia.
     *
     * @param accettata true se l'amicizia è stata accettata.
     */
    public void setAccettata(boolean accettata) {
        this.accettata = accettata;
    }

    /**
     * Riceve e inoltra l'accettazione dell'amicizia all'indirizzo email.
     *
     * @throws ConditionException se il campo email è nulla
     */
    @Override
    public void esegui() throws ConditionException {
        checkArg(email != null);

        IUtenteRegistrato altro = getGestoreUtenti().getUtenteRegistrato(email);

        if (!accettata) {
            altro.rifiutaAmiciziaCon(getConnessioneMittente().getEmail());
        } else {
            altro.accettaAmiciziaCon(getConnessioneMittente().getEmail());

            {
                NotificaListaAggiungi n =
                        getFactotyNotifiche().creaNotificaListaAggiungi();
                n.setEmail(altro.getEmail());
                n.setStato(altro.getStato());
                n.setUsername(altro.getUserName());
                getConnessioneMittente().send(n.serializza());
            }

            try {
                IUtenteRegistrato u = getUtenteRegistrato();
                NotificaListaAggiungi n =
                        getFactotyNotifiche().creaNotificaListaAggiungi();
                n.setEmail(u.getEmail());
                n.setUsername(u.getUserName());
                n.setStato(u.getStato());

                IConnessione c = getRegistroConnessioni().getConnessione(email);
                checkArg(c != null);
                c.send(n.serializza());
            } catch (ConditionException ex) {
                //ignoro
            }
        }
    }
}
