/* file:        GestoreMessaggioSegreteria.java
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

import it.pt.mt.srv.util.ConditionException;
import static it.pt.mt.srv.util.CheckArg.checkArg;

/**
 * Rappresenta il gestore per l'EventoMessaggioSegreteria.
 */
public class GestoreMessaggioSegreteria
        extends AbstractGestore implements GestoreRegistrato {

    private String destinatario;
    private String messaggio;

    /**
     * Imposta il destinatario.
     *
     * @param destinatario l'email o ip del destinatario
     */
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    /**
     * Imposta un messaggio
     *
     * @param messaggio
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    /**
     * Inserisce nel database un messaggio di segreteria e invia una notifica di conferma.
     *
     * @throws ConditionException se uno dei campi destinatario o messaggio sono
     * nulli
     */
    @Override
    public void esegui() throws ConditionException {

        checkArg(destinatario != null);
        checkArg(messaggio != null);

        getGestoreUtenti().getUtenteRegistrato(destinatario)
                .nuovoMessaggio(getConnessioneMittente().getEmail(), messaggio);

        inviaNotificaConferma("MessaggioSegreteria");
    }
}
