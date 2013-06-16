/* file:        GestoreRicercaUtenti.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.1
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-17  TF  Prima stesura
 * 1.1  2013-06-03  TF  Aggiungo controllo sulle amicizie esistenti
 */
package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.notifiche.NotificaEsitiRicerca;
import it.pt.mt.srv.model.IUtenteRegistrato;
import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rappresenta il gestore per l'EventoRicercaUtenti.
 */
public class GestoreRicercaUtenti
        extends AbstractGestore implements GestoreRegistrato {

    private String pattern;

    /**
     * Imposta il pattern di ricerca.
     *
     * @param pattern pattern di ricerca
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Ricerca nel modello il pattern e invia una
     * NotificaEsitiRicerca con gli utenti che non sono già amici.
     *
     * @throws ConditionException se pattern è nullo
     */
    @Override
    public void esegui() throws ConditionException {
        checkArg(pattern != null);

        List<IUtenteRegistrato> amici = getUtenteRegistrato().getAmici();
        List<IUtenteRegistrato> amiciziePendenti = getUtenteRegistrato().getAmiciziePendenti();

        List<IUtenteRegistrato> trovati =
                getGestoreUtenti().ricercaUtenti(pattern);
        NotificaEsitiRicerca n =
                getFactotyNotifiche().creaNotificaEsitiRicerca();
        Map<String, String> l = new HashMap<String, String>(trovati.size());

        for (IUtenteRegistrato t : trovati) {
            if ((!amici.contains(t)) && (!amiciziePendenti.contains(t))
                    && (!getUtenteRegistrato().getEmail().equals(t.getEmail()))) {
                l.put(t.getEmail(), t.getUserName());
            }
        }

        n.setMappa(l);
        getConnessioneMittente().send(n.serializza());
    }
}
