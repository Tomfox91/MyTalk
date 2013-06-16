/* file:        NotificaSegnale.java
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

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Contiene il segnale che un altro Client ha inviato.
 */
public class NotificaSegnale extends Notifica {

    private String mittente;
    private String sottotipo;
    private int porta;
    private JsonNode segnale;

    /**
     * Restituisce il tipo della Notifica.
     *
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "Segnale";
    }

    /**
     * Restituisce l'email dell'utente se esso è autenticato o l'ip
     * se non lo è.
     *
     * @return l'email o l'ip dell'utente
     */
    public String getMittente() {
        return mittente;
    }

    /**
     * Imposta il mittete della notifica con l'email o l'ip.
     *
     * @param mittente l'email o l'ip da impostare nella notifica
     */
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    /**
     * Restituisce il sottotipo del segnale.
     *
     * @return il sottotipo del segnale
     */
    public String getSottotipo() {
        return sottotipo;
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
     * Restituisce il segnale da inviare all'altro Client.
     *
     * @return segnale da inviare all'altro Client
     */
    public JsonNode getSegnale() {
        return segnale;
    }

    /**
     * Imposta il segnale da inviare all'altro Client.
     *
     * @param segnale segnale da inviare all'altro Client
     */
    public void setSegnale(JsonNode segnale) {
        this.segnale = segnale;
    }

    /**
     * Restituisce la porta di destinazione.
     *
     * @return la porta di destinazione
     */
    public int getPorta() {
        return porta;
    }

    /**
     * Imposta la porta di destinazione.
     *
     * @param porta la porta di destinazione
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }
}
