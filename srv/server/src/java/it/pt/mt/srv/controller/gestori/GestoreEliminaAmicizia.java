/* file:        GestoreEliminaAmicizia.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-05-17  TF  Prima stesura
 */
package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.NotificaListaAggiungi;
import it.pt.mt.srv.controller.notifiche.NotificaListaElimina;
import it.pt.mt.srv.model.IUtenteRegistrato;
import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta il gestore per l'EventoEliminaAmicizia.
 */
public class GestoreEliminaAmicizia
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
     * Elimina l'amicizia tra il mittente e email; invia a ciascuno una
     * NotificaListaElimina.
     *
     * @throws ConditionException se il campo email è nulla
     */
    @Override
    public void esegui() throws ConditionException {
        checkArg(email != null);

        getUtenteRegistrato().cancellaAmiciziaCon(email);

        {
            NotificaListaElimina n =
                    getFactotyNotifiche().creaNotificaListaElimina();
            n.setEmail(email);
            getConnessioneMittente().send(n.serializza());
        }

        try {
            NotificaListaElimina n =
                    getFactotyNotifiche().creaNotificaListaElimina();
            n.setEmail(getUtenteRegistrato().getEmail());

            IConnessione c = getRegistroConnessioni().getConnessione(email);
            checkArg(c != null);
            c.send(n.serializza());
        } catch (ConditionException ex) {
            //ignoro
        }
    }
}
