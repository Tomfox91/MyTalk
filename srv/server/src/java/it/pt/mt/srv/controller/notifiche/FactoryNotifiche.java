/* file:        FactoryNotifiche.java
 * package:     it.pt.mt.srv.controller.notifiche
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 */
package it.pt.mt.srv.controller.notifiche;

/**
 * Fornisce un Singleton per la creazione di notifiche.
 */
public class FactoryNotifiche {

    private static FactoryNotifiche instance;

    /**
     * Restituisce l'istanza della notifica.
     * @return l'istanza della notifica
     */
    public static FactoryNotifiche getInstance() {
        if (instance == null) {
            instance = new FactoryNotifiche();
        }
        return instance;
    }
    /**
     * Costruttore vuoto e privato.
     */
    private FactoryNotifiche() {
    }

    /**
     * Restituisce una DomandaSicurezza.
     * @return la nuova istanza creata
     */
    public NotificaDomandaSicurezza creaNotificaDomandaSicurezza() {
        return new NotificaDomandaSicurezza();
    }

    /**
     * Restituisce una NotificaEsitiRicerca.
     * @return la nuova istanza creata
     */
    public NotificaEsitiRicerca creaNotificaEsitiRicerca() {
        return new NotificaEsitiRicerca();
    }

    /**
     * Restituisce una NotificaSegnale.
     * @return la nuova istanza creata
     */
    public NotificaSegnale creaNotificaSegnale() {
        return new NotificaSegnale();
    }

    /**
     * Restituisce una NotificaListaAggiungi.
     * @return la nuova istanza creata
     */
    public NotificaListaAggiungi creaNotificaListaAggiungi() {
        return new NotificaListaAggiungi();
    }

    /**
     * Restituisce una NotificaListaElimina.
     * @return la nuova istanza creata
     */
    public NotificaListaElimina creaNotificaListaElimina() {
        return new NotificaListaElimina();
    }

    /**
     * Restituisce una NotificaListaModifica.
     * @return la nuova istanza creata
     */
    public NotificaListaModifica creaNotificaListaModifica() {
        return new NotificaListaModifica();
    }

    /**
     * Restituisce una NotificaNuovaRichiestaAmicizia.
     * @return la nuova istanza creata
     */
    public NotificaNuovaRichiestaAmicizia creaNotificaNuovaRichiestaAmicizia() {
        return new NotificaNuovaRichiestaAmicizia();
    }

    /**
     * Restituisce una NotificaNuovoMessaggio.
     * @return la nuova istanza creata
     */
    public NotificaNuovoMessaggio creaNotificaNuovoMessaggio() {
        return new NotificaNuovoMessaggio();
    }

    /**
     * Restituisce una NotificaOperazione.
     * @return la nuova istanza creata
     */
    public NotificaOperazione creaNotificaOperazione() {
        return new NotificaOperazione();
    }

    /**
     * Restituisce una NotificaProprioIP.
     * @return la nuova istanza creata
     */
    public NotificaProprioIP creaNotificaProprioIP() {
        return new NotificaProprioIP();
    }

    /**
     * Restituisce una NotificaProprioUtente.
     * @return la nuova istanza creata
     */
    public NotificaProprioUtente creaNotificaProprioUtente() {
        return new NotificaProprioUtente();
    }
}
