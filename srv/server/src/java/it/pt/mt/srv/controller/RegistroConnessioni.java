/* file:        RegistroConnessioni.java
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

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.util.Hash;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mantiene il registro delle connessioni attive. Salva solo uno hash
 * dell'identificativo (ip o email) della connessione, oltre al
 * riferimento alla medesima. Internamente i riferimenti sono mantenuti in 256
 * oggetti di tipo Ramo (la scelta del Ramo viene effettuata sulla
 * base dell'ultimo byte dello hash. Quest'architettura della classe
 * permette, in caso di necessità, di distribuire i Ramo in diverse
 * macchine. Una connessione il cui utente non è autenticato sarà presente nel
 * registro solo con l'ip come chiave, mentre una connessione il cui
 * utente è autenticato sarà presente due volte, con chiavi l'ip e
 * l'email.
 */
public class RegistroConnessioni {

    private static RegistroConnessioni instance;
    private final Ramo[] array = new Ramo[256];

    /**
     * Restituisce, applicando il design pattern Singleton, l'istanza del
     * registro.
     *
     * @return l'istanza del registro
     */
    public static RegistroConnessioni getInstance() {
        if (instance == null) {
            instance = new RegistroConnessioni();
        }
        return instance;
    }

    /**
     * Costruttore privato; crea un Ramo per ogni possibile valore di un
     * byte.
     */
    private RegistroConnessioni() {
        for (int i = 0; i < 256; i++) {
            array[i] = new Ramo();
        }
    }

    /**
     * Restituisce il Ramo appropriato a partire da uno hash.
     *
     * @param hash hash
     */
    private Ramo trovaRamo(Hash hash) {
        int i = hash.ultimoByte() + 128;
        return array[i];
    }

    /**
     * Aggiunge una connessione al RegistroConnessioni. L'operazione viene
     * comunicata ad un Logger.
     *
     * @param ipOEmail un ip o una email
     * @param c la IConnessione da salvare
     */
    public void aggiungiConnessione(String ipOEmail, IConnessione c) {
        Logger.getLogger("mytalk").log(Level.INFO, "Inserita connessione: {0}", ipOEmail);
        Hash hash = new Hash(ipOEmail);
        trovaRamo(hash).aggiungiConnessione(hash, c);
    }

    /**
     * Restituisce la connessione corrispondente all'ip o l'email
     * specificati.
     *
     * @param ipOEmail un ip o una email
     * @return la connessione corrispondente all'ip o all'email
     * specificati
     */
    public IConnessione getConnessione(String ipOEmail) {
        Hash hash = new Hash(ipOEmail);
        return trovaRamo(hash).getConnessione(hash);
    }

    /**
     * Rimuove la connessione corrispondente all'ip o email
     * specificata. L'operazione viene comunicata ad un Logger.
     *
     * @param ipOEmail un ip o una email
     */
    public void rimuoviConnessione(String ipOEmail) {
        Logger.getLogger("mytalk").log(Level.INFO, "Rimossa connessione: {0}", ipOEmail);
        Hash hash = new Hash(ipOEmail);
        trovaRamo(hash).rimuoviConnessione(hash);
    }

    /**
     * Mantiene una ConcurrentHashMap di IConnessione.
     */
    private static class Ramo {

        private ConcurrentHashMap<Hash, IConnessione> mappa =
                new ConcurrentHashMap<Hash, IConnessione>();

        /**
         * Aggiunge una connessione.
         *
         * @param hash hash dell'identificativo
         * @param c IConnessione
         */
        public void aggiungiConnessione(Hash hash, IConnessione c) {
            mappa.put(hash, c);
        }

        /**
         * Restituisce la connessione specificata dallo hash.
         *
         * @param hash hash dell'identificativo
         * @return la connessione specificata dallo hash
         */
        public IConnessione getConnessione(Hash hash) {
            return mappa.get(hash);
        }

        /**
         * Rimuove la connessione specificata dallo hash.
         *
         * @param hash hash dell'identificativo della connessione da
         * rimuovere
         */
        public void rimuoviConnessione(Hash hash) {
            mappa.remove(hash);
        }
    }
}
