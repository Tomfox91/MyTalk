/* file:        CreatoreConnessioni.java
 * package:     it.pt.mt.srv.controller.network
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 */
package it.pt.mt.srv.controller.network;

import it.pt.mt.srv.controller.Dispatcher;
import it.pt.mt.srv.controller.RegistroConnessioni;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * Gestisce la creazione di connessioni all'arrivo di una richiesta.
 */
public class CreatoreConnessioni extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la connessione per la nuova richiesta arrivata.
     *
     * @param subProtocol il protocollo utilizzato
     * @param request richiesta http
     * @return restituisce la connessione creata
     */
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {

        String ip = request.getRemoteAddr();

        Connessione c = new Connessione(ip);
        c.getSubject().addObserver(Dispatcher.getInstance());
        RegistroConnessioni.getInstance().aggiungiConnessione(ip, c);
        return c;
    }
}
