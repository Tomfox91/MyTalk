/* file:        Dispatcher.java
 * package:     it.pt.mt.srv.controller
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 */
package it.pt.mt.srv.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.pt.mt.srv.controller.gestori.Gestore;
import it.pt.mt.srv.controller.gestori.GestoreIp;
import it.pt.mt.srv.controller.gestori.GestoreLogout;
import it.pt.mt.srv.controller.gestori.GestoreRegistrato;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
import java.util.Observable;
import java.util.Observer;
import static it.pt.mt.srv.util.CheckArg.*;
import it.pt.mt.srv.util.ConditionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ha riferimenti alla Connessione ed all'oggetto IUtente che è vi è
 * collegato; ha lo scopo di ricevere gli EventiIn provenienti dalla
 * Connessione (che vengono comunicati in forma serializzata tramite
 * l'Observer).
 */
public class Dispatcher implements Observer {

    private static Dispatcher instance;

    /**
     * Restituisce l'istanza del presente Dispatcher.
     *
     * @return l'istanza del Dispatcher
     */
    public static Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    /**
     * Costruttore privato e vuoto.
     */
    private Dispatcher() {
    }

    /**
     * Viene chiamata quando una connessione viene interrotta. Se l'utente è
     * autenticato crea un GestoreLogout per gestire il logout
     * d'ufficio; inoltre informa il RegistroConnessione di rimuovere il
     * riferimento dall'ip alla connessione stessa.
     *
     * @param c IConnessione da rimuovere
     */
    private void gestisciConnessioneDisconnessa(IConnessione c) {
        String email = c.getEmail();
        if (email != null) {
            try {
                GestoreLogout g = new GestoreLogout();
                g.setMittente(c);
                g.esegui();
            } catch (ConditionException ex) {
                //non succede
            }
        }
        RegistroConnessioni.getInstance().rimuoviConnessione(c.getIP());
    }

    /**
     * Implementa l'update dell'Observer. Deserializza gli eventi in
     * ingresso; se l'evento è nullo chiama il metodo privato
     * connessioneDisconnessa, altrimenti deserializza l'evento ricevuto,
     * costruisce tramite reflection un Gestore adeguato, imposta su
     * di esso i parametri ricevuti, e lo esegue. Se il Gestore solleva
     * un'eccezione, lo comunica al Logger e invia al mittente una
     * NotificaOperazione che indica il fallimento dell'operazione.
     *
     * @param o è la connessione del mittente
     * @param arg è l'implementazione di una interfaccia
     */
    @Override
    public void update(Observable o, Object arg) {
        assertArg(o instanceof IConnessione.ISubject);
        IConnessione mitt = ((IConnessione.ISubject) o).getConnessione();

        if (arg == null) {
            gestisciConnessioneDisconnessa(mitt);
        } else {

            assertArg(arg instanceof String);
            String stringaEvento = (String) arg;
            String tipoEvento = "";

            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readValue(stringaEvento, JsonNode.class);

                JsonNode nodoTipo = root.get("tipo");
                checkArg(nodoTipo != null);
                tipoEvento = nodoTipo.asText();
                checkArg(tipoEvento.length() > 0);

                JsonNode nodoEvento = root.get("evento");
                checkArg(nodoEvento != null);


                String classe = "it.pt.mt.srv.controller.gestori.Gestore"
                        + tipoEvento;

                Class<? extends Gestore> cgest;
                cgest = Class.forName(classe).
                        asSubclass(superclasseGestoreDaMittente(mitt));
                Gestore gest = mapper.treeToValue(nodoEvento, cgest);
                gest.setMittente(mitt);
                gest.esegui();

            } catch (Exception ex) {
                Logger.getLogger("mytalk")
                        .log(Level.WARNING, "Eccezione Dispatcher", ex);
                NotificaOperazione notifica =
                        FactoryNotifiche.getInstance().creaNotificaOperazione();
                notifica.setRiuscita(false);
                notifica.setOriginale(tipoEvento);
                mitt.send(notifica.serializza());
            }
        }
    }

    /**
     * Restituisce l'interfaccia che il gestore dell'evento deve implementare, a
     * seconda del tipo dell'utente. Se è autenticato restituisce
     * GestoreRegistrato.class, altrimenti GestoreIp.class.
     *
     * @param mittente IConnessione
     * @return l'interfaccia che il gestore dell'evento deve implementare
     */
    private Class<? extends Gestore> superclasseGestoreDaMittente(IConnessione mittente) {
        if (mittente.getEmail() == null) {
            return GestoreIp.class;
        } else {
            return GestoreRegistrato.class;
        }
    }
}
