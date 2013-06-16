/* file:        Connessione.java
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

import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaProprioIP;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Observable;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

/**
 * La classe riceve i messaggi dalla rete e li inoltrara alle classi appropriate del Controller;
 * è l'implementa quindi il Subject nel design pattern Observer applicato tra
 * Controller e Network.
 */
public class Connessione extends MessageInbound implements IConnessione {

    private String IP;
    private String email;
    private Subject subject = new Subject();

    /**
     * Restituisce il Subject.
     *
     * @return restituisce un Subject
     */
    public Observable getSubject() {
        return subject;
    }

    /**
     * Restituisce l'ip della connessione specifica.
     *
     * @return la stringa contenente l'ip della connessione
     */
    @Override
    public String getIP() {
        return IP;
    }

    /**
     * Restituisce l'email di colui che ha creato la connessione lato
     * client.
     *
     * @return la stringa contenente l'email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email di colui che ha creato la connessione.
     *
     * @param email l'email da impostare
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Costruisce la connessione a partire dall'ip di colui che ha
     * iniziato la richiesta.
     *
     * @param IP
     */
    public Connessione(String IP) {
        this.IP = IP;

        setByteBufferMaxSize(2097152);
        setCharBufferMaxSize(2097152);
    }

    /**
     * Invia al Client il suo ip.
     */
    @Override
    protected void onOpen(WsOutbound outbound) {
        NotificaProprioIP n = FactoryNotifiche.getInstance().creaNotificaProprioIP();
        n.setIP(getIP());
        send(n.serializza());
    }

    /**
     * Invia il messaggio sulla connessione WebSocket.
     *
     * @param messaggio la stringa contenente il messaggio da inviare.
     */
    @Override
    public void send(String messaggio) {
        CharBuffer buf = CharBuffer.wrap(messaggio);
        try {
            getWsOutbound().writeTextMessage(buf);
        } catch (IOException ex) {
            clientDisconnesso();
        }
    }

    /**
     * Esegue le operazioni da compiere durante la disconnessione.
     *
     * @param status lo status
     */
    @Override
    protected void onClose(int status) {
        clientDisconnesso();
    }
    private boolean disconnesso = false;

    /**
     * Disconnette il Client.
     */
    private synchronized void clientDisconnesso() {
        if (!disconnesso) {
            disconnesso = true;
            subject.notifyObservers(null);
        }
    }

    /**
     * Operazione da compiere all'arrivo di dati binari.
     *
     * @param message il messaggio arrivato
     * @throws IOException Restituisce errore se arrivano dati binari
     */
    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        getWsOutbound().close(400, null);
    }

    /**
     * Operazioni da eseguire all'arrivo di un messaggio sulla connessione.
     * WebSocket.
     *
     * @param message il messaggio da inviare
     */
    @Override
    protected void onTextMessage(CharBuffer message) {
        String messaggio = message.toString();
        subject.notifyObservers(messaggio);
    }

    /**
     * è una classe interna che funge da Controller.
     */
    private class Subject extends Observable implements IConnessione.ISubject {

        /**
         * Implementa il metodo notify() del design pattern
         * Observer.
         */
        @Override
        public void notifyObservers(Object arg) {
            setChanged();
            super.notifyObservers(arg);
        }

        /**
         * Restituisce la connessione legata a questa classe.
         */
        @Override
        public IConnessione getConnessione() {
            return Connessione.this;
        }
    }
}
