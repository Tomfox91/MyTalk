/* file:        GestoreModificaProfilo.java
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

import it.pt.mt.srv.controller.notifiche.NotificaListaModifica;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

/**
 * Rappresenta il gestore per l'EventoModificaProfilo.
 */
public class GestoreModificaProfilo
        extends AbstractGestore implements GestoreRegistrato {

    private String username;
    private String stato;

    /**
     * Imposta lo username.
     *
     * @param username lo username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Imposta lo stato.
     *
     * @param stato
     */
    public void setStato(String stato) {
        this.stato = stato;
    }

    /**
     * Modifica il profilo e manda una notifica agli amici connessi.
     *
     * @throws ConditionException se uno dei campi, username o stato, sono
     * nulli
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(username != null);
        checkArg(stato != null);

        IUtenteRegistrato u = getUtenteRegistrato();
        u.setStato(stato);
        u.setUserName(username);

        inviaNotificaConferma("ModificaProfilo");

        NotificaListaModifica n =
                getFactotyNotifiche().creaNotificaListaModifica();
        n.setEmail(getConnessioneMittente().getEmail());
        n.setStato(stato);
        n.setUsername(username);
        inviaAdAmici(n);
    }
}
