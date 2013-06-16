/* file:        GestoreSegnale.java
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

import com.fasterxml.jackson.databind.JsonNode;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.NotificaSegnale;
import it.pt.mt.srv.model.IUtente;
import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

/**
 * Rappresenta il gestore per l'EventoSegnale.
 */
public class GestoreSegnale
        extends AbstractGestore implements GestoreIp, GestoreRegistrato {

    private String destinatario;
    private String sottotipo;
    private int porta;
    private JsonNode segnale;

    /**
     * Imposta il destinatario della chiamata.
     *
     * @param destinatario l'email o ip del destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Imposta il sottotipo del segnale.
     *
     * @param sottotipo il sottotipo del segnale
     */
    public void setSottotipo(String sottotipo) {
        this.sottotipo = sottotipo;
    }

    /**
     * Imposta la porta di destinazione.
     *
     * @param porta la porta di destinazione
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }

    /**
     * Imposta il corpo del segnale.
     *
     * @param segnale corpo del segnale
     */
    public void setSegnale(JsonNode segnale) {
        this.segnale = segnale;
    }

    /**
     * Inoltra il segnale al destinatario.
     *
     * @throws ConditionException se uno dei campi richiesti è nullo
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(destinatario != null);
        checkArg(sottotipo != null);
        checkArg(segnale != null);

        NotificaSegnale n =
                getFactotyNotifiche().creaNotificaSegnale();
        n.setMittente(getMittente());
        n.setSottotipo(sottotipo);
        n.setSegnale(segnale);
        n.setPorta(porta);

        IConnessione connessione = getRegistroConnessioni()
                .getConnessione(getDestinatario().getNome());
        checkArg(connessione != null);
        connessione.send(n.serializza());
    }

    /**
     * Restituisce l'identificativo del mittente della chiamata.
     *
     * @return email del mittente se è Autenticato oppure ip se non
     * lo è
     */
    private String getMittente() {
        if (getConnessioneMittente().getEmail() != null) {
            return getConnessioneMittente().getEmail();
        } else { //mittente ip
            return getConnessioneMittente().getIP();
        }
    }

    /**
     * Restituisce la rappresentazione del destinatario.
     *
     * @return email del destinatario se è Autenticato oppure ip se
     * non lo è
     * @throws ConditionException
     */
    private IUtente getDestinatario() throws ConditionException {
        if (destinatario.contains("@")) { //destinatario email
            return getGestoreUtenti().getUtenteRegistrato(destinatario);
        } else { //destinatario ip
            return getGestoreUtenti().getUtenteIP(destinatario);
        }
    }
}
