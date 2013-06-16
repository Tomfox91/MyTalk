/* file:        GestoreAltroEventoFinto.java
 * package:     it.pt.mt.srv.controller.gestori
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 *
 * Serve a DispatcherTest
 */


package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import java.util.List;

public class GestoreAltroEventoFinto implements GestoreRegistrato {

    private List<String> lista;
    private double pluto;
    private IConnessione mittente;

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public void setPluto(double pluto) {
        this.pluto = pluto;
    }

    @Override
    public void setMittente(IConnessione mittente) {
        this.mittente = mittente;
    }

    @Override
    public void esegui() {
        String res = "";
        for (String i : lista) {
            res += i + ' ';
        }
        res += pluto;
        mittente.send(res);
    }


}
