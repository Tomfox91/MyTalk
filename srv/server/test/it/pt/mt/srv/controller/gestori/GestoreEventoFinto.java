/* file:        GestoreEventoFinto.java
 * package:     it.pt.mt.srv.controller.network.eventi
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 *
 * Serve a DispatcherTest
 */

package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;

public class GestoreEventoFinto implements GestoreIp {

    private String campo1;
    private int campo2;
    private IConnessione mittente;

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public void setCampo2(int campo2) {
        this.campo2 = campo2;
    }

    @Override
    public void setMittente(IConnessione mittente) {
        this.mittente = mittente;
    }

    @Override
    public void esegui() {
        mittente.send(campo1 + ' ' + campo2);
    }
}
