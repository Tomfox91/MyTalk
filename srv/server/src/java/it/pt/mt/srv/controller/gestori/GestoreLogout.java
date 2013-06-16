/* file:        GestoreLogout.java
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

import it.pt.mt.srv.controller.notifiche.NotificaListaModifica;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta il gestore per l'EventoLogout.
 */
public class GestoreLogout
        extends AbstractGestore implements GestoreRegistrato {

    /**
     * Rimuove l'associazione dell'email con l'utente e notifica ai suoi
     * amici connessi il suo nuovo stato (offline)
     *
     * @throws ConditionException
     */
    @Override
    public void esegui() throws ConditionException {
        IUtenteRegistrato u = getUtenteRegistrato();
        String email = getConnessioneMittente().getEmail();
        u.setStato("Offline");
        getRegistroConnessioni().rimuoviConnessione(email);

        NotificaListaModifica n =
                getFactotyNotifiche().creaNotificaListaModifica();
        n.setEmail(email);
        n.setStato("Offline");
        n.setUsername(u.getUserName());

        inviaAdAmici(n);
        getConnessioneMittente().setEmail(null);
    }
}
