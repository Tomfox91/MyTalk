/* file:        GestoreModificaPassword.java
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

import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

/**
 * Rappresenta il gestore per l'EventoModificaPassword.
 */
public class GestoreModificaPassword
        extends AbstractGestore implements GestoreRegistrato {

    private String vecchia;
    private String nuova;

    /**
     * Imposta la vecchia password
     *
     * @param vecchia la vecchia password da impostare
     */
    public void setVecchia(String vecchia) {
        this.vecchia = vecchia;
    }

    /**
     * Imposta la nuova password
     *
     * @param nuova la nuova password da impostare
     */
    public void setNuova(String nuova) {
        this.nuova = nuova;
    }

    /**
     * Modifica la password e invia una notifica di conferma.
     *
     * @throws ConditionException se uno dei campi, vecchia o nuova, è nullo
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(vecchia != null);
        checkArg(nuova != null);

        boolean ok = getUtenteRegistrato().cambiaPassword(vecchia, nuova);

        checkArg(ok);
        inviaNotificaConferma("ModificaPassword");
    }
}
