/* file:        NotificaFinta.java
 * package:     it.pt.mt.srv.controller.notifiche
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-15  TF  Prima stesura
 *
 * Usato da EventoOutTest
 */


package it.pt.mt.srv.controller.notifiche;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NotificaFinta extends Notifica {

    private String pippo = "pluto";
    private List<Integer> lista =
            new ArrayList<Integer>(Arrays.asList(21, 42, 84));

    @Override
    public String getTipo() {
        return "Finta";
    }

    public String getPippo() {
        return pippo;
    }

    public List<Integer> getLista() {
        return Collections.unmodifiableList(lista);
    }

    @JsonIgnore
    public int getValoreLocale() {
        return 561514;
    }

}
