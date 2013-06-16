/* file:        AbstractGestore.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.1
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-16  TF  Prima stesura
 * 1.1  2013-06-03  TF  Rimuovo metodo inutile getUtenteIP
 */
package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.Notifica;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;

/**
 * Rappresenta un generico gestore e offre alcuni metodi di utilità.
 *
 */
abstract class AbstractGestore {
    private IConnessione mittente;

    /**
     * Restituisce il GestoreUtenti.
     *
     * @return gut GestoreUtenti
     */
    protected GestoreUtenti getGestoreUtenti() {
        return GestoreUtenti.getInstance();
    }

    /**
     * Restituisce la FactoryNotifiche.
     *
     * @return fnot FactoryNotifiche
     */
    protected FactoryNotifiche getFactotyNotifiche() {
        return FactoryNotifiche.getInstance();
    }

    /**
     * Restituisce il RegistroConnessioni.
     *
     * @return rcon RegistroConnessioni
     */
    protected RegistroConnessioni getRegistroConnessioni() {
        return RegistroConnessioni.getInstance();
    }

    /**
     * Restituisce la ConnessioneMittente.
     *
     * @return mittente ConnessioneMittente
     */
    protected IConnessione getConnessioneMittente() {
        return mittente;
    }

    /**
     * Imposta il mittente.
     *
     * @param connessione IConnessione
     */
    public void setMittente(IConnessione connessione) {
        this.mittente = connessione;
    }

    /**
     * Restituisce l'UtenteRegistrato del mittente.
     *
     * @return la rappresentazione dell'UtenteRegistrato collegato
     */
    protected IUtenteRegistrato getUtenteRegistrato() {
        try {
            return GestoreUtenti.getInstance().getUtenteRegistrato(mittente.getEmail());
        } catch (ConditionException ex) {
            throw new AssertionError();
        }
    }

    /**
     * Invia una notifica di conferma al mittente.
     *
     * @param eventoOriginale nome dell'operazione che viene confermata
     */
    protected void inviaNotificaConferma(String eventoOriginale) {
        NotificaOperazione n = getFactotyNotifiche().creaNotificaOperazione();
        n.setOriginale(eventoOriginale);
        n.setRiuscita(true);
        getConnessioneMittente().send(n.serializza());
    }

    /**
     * Invia una notifica a tutti gli amici connesi
     *
     * @param n la notifica da serializzare e inviare
     */
    protected void inviaAdAmici(Notifica n) {
        String serializzata = n.serializza();

        for (IUtenteRegistrato a : getUtenteRegistrato().getAmici()) {
            IConnessione connessione =
                    getRegistroConnessioni().getConnessione(a.getEmail());
            if (connessione != null) {
                connessione.send(serializzata);
            }
        }
    }
}
