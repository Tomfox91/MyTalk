/* file:        GestoreRichiestaDomandaSicurezza.java
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

import it.pt.mt.srv.controller.notifiche.NotificaDomandaSicurezza;
import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

/**
 * Rappresenta il gestore per l'EventoRichiestaDomandaSicurezza.
 */
public class GestoreRichiestaDomandaSicurezza
        extends AbstractGestore implements GestoreIp {

    private String email;

    /**
     * Imposta l'email.
     *
     * @param email l'email da impostare
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Richiede la domanda di sicurezza e la notifica al mittente.
     *
     * @throws ConditionException se l'email è nulla
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(email != null);

        String dom = getGestoreUtenti().getUtenteRegistrato(email).getDomanda();
        NotificaDomandaSicurezza n =
                getFactotyNotifiche().creaNotificaDomandaSicurezza();
        n.setDomanda(dom);
        getConnessioneMittente().send(n.serializza());
    }
}
