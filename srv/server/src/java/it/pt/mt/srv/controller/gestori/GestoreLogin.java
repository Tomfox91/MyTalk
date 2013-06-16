/* file:        GestoreLogin.java
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

import it.pt.mt.srv.controller.notifiche.NotificaListaAggiungi;
import it.pt.mt.srv.controller.notifiche.NotificaListaModifica;
import it.pt.mt.srv.controller.notifiche.NotificaNuovaRichiestaAmicizia;
import it.pt.mt.srv.controller.notifiche.NotificaNuovoMessaggio;
import it.pt.mt.srv.controller.notifiche.NotificaProprioUtente;
import it.pt.mt.srv.model.IMessaggio;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

/**
 * Rappresenta il gestore per l'EventoLogin.
 */
public class GestoreLogin
        extends AbstractGestore implements GestoreIp {

    private String email;
    private String password;

    /**
     * Imposta la email.
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Imposta la password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Autentica l'utente e gli invia una notifica per ogni utente della sua
     * lista amici connesso, per ogni richiesta pendente e per ogni messaggio.
     * Invia una notifica agli amici connessi dell'utente appena autenticato per
     * aggiornarli del suo nuovo stato.
     *
     * @throws ConditionException se non viene trovata l'email tra gli
     * utenti registrati al server oppure se la password è sbagliata
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(email != null);
        checkArg(password != null);

        //solleva un'eccezione se non lo trova
        IUtenteRegistrato ur = getGestoreUtenti().getUtenteRegistrato(email);

        //solleva un'eccezione se è sbagliata
        checkArg(ur.checkPassword(password));

        getConnessioneMittente().setEmail(email);
        getRegistroConnessioni()
                .aggiungiConnessione(email, getConnessioneMittente());
        ur.setStato("Online");

        //mando proprio utente
        {
            NotificaProprioUtente n =
                    getFactotyNotifiche().creaNotificaProprioUtente();
            n.setEmail(ur.getEmail());
            n.setStato(ur.getStato());
            n.setUsername(ur.getUserName());
            getConnessioneMittente().send(n.serializza());
        }

        //mando gli amici
        for (IUtenteRegistrato a : ur.getAmici()) {
            NotificaListaAggiungi n =
                    getFactotyNotifiche().creaNotificaListaAggiungi();
            n.setEmail(a.getEmail());
            n.setStato(a.getStato());
            n.setUsername(a.getUserName());
            getConnessioneMittente().send(n.serializza());
        }

        //mando le richieste pendenti
        for (IUtenteRegistrato a : ur.getAmiciziePendenti()) {
            NotificaNuovaRichiestaAmicizia n =
                    getFactotyNotifiche().creaNotificaNuovaRichiestaAmicizia();
            n.setEmail(a.getEmail());
            n.setUsername(a.getUserName());
            getConnessioneMittente().send(n.serializza());
        }

        //mando i messaggi
        for (IMessaggio m : ur.getMessaggi()) {
            NotificaNuovoMessaggio n =
                    getFactotyNotifiche().creaNotificaNuovoMessaggio();
            n.setMessaggio(m.getMessaggio());
            n.setMittente(m.getMittente());
            getConnessioneMittente().send(n.serializza());
        }

        //informo gli amici
        {
            NotificaListaModifica n =
                    getFactotyNotifiche().creaNotificaListaModifica();
            n.setStato("Online");
            n.setEmail(email);
            n.setUsername(ur.getUserName());
            inviaAdAmici(n);
        }
    }
}
