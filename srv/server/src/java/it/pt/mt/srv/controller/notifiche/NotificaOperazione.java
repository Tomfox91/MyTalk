/* file:        NotificaOperazione.java
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
 * Viene inviata ad un utente che è già coinvolto in una conferenza per
 * comunicargli che uno degli altri membri ha aggiunto una persona alla confe-
 * renza; ilClientche riceve questa notifica deve quindi produrre ed
 * inviare un EventoInvitoChiamata per connettersi al nuovo partecipante.
 */
public class NotificaOperazione extends Notifica {

    private boolean riuscita;
    private String originale;

    /**
     * Restituisce il tipo della Notifica.
     *
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "Operazione";
    }

    /**
     * Restituisce lo stato della notifica.
     *
     * @return Restituisce true se la notifica è riuscuta
     */
    public boolean isRiuscita() {
        return riuscita;
    }

    /**
     * Imposta lo stato della notifica.
     *
     * @param riuscita lo stato della notifica da impostare
     */
    public void setRiuscita(boolean riuscita) {
        this.riuscita = riuscita;
    }

    /**
     * Restituisce la stringa con l'email della persona da aggiungere.
     *
     * @return l'email della persona da aggiungere
     */
    public String getOriginale() {
        return originale;
    }

    /**
     * Imposta l'email della persona da aggiungere.
     *
     * @param originale l'email della persona da aggiungere da impostare
     */
    public void setOriginale(String originale) {
        this.originale = originale;
    }
}
