/* file:        NotificaProprioIP.java
 * package:     it.pt.mt.srv.controller.notifiche
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-27  TF  Prima stesura
 */
package it.pt.mt.srv.controller.notifiche;

/**
 * Indica al Client il proprio ip.
 */
public class NotificaProprioIP extends Notifica {

    private String IP;

    /**
     * Restituisce il tipo della Notifica.
     *
     * @return Restituisce una stringa contenente il tipo della notifica
     */
    @Override
    public String getTipo() {
        return "ProprioIP";
    }

    /**
     * Restituisce l'ip.
     *
     * @return l'ip
     */
    public String getIP() {
        return IP;
    }

    /**
     * Imposta l'ip.
     *
     * @param ip l'ip da inserire nella notifica
     */
    public void setIP(String ip) {
        this.IP = ip;
    }
}
