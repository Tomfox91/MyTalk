/* file:        DBConf.java
 * package:     it.pt.mt.srv.util
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-18  TF  Prima stesura
 */
package it.pt.mt.srv.util;

/**
 * Contiene le impostazioni di configurazione del database.
 */
public class DBConf {

    private static String path = "db/mytalkdb";

    /**
     * Costruttore vuoto privato.
     */
    private DBConf() {
    }

    /**
     * Restituisce il percorso del database.
     *
     * @return il percorso del database
     */
    public static String getPath() {
        return path;
    }

    /**
     * Imposta il percorso del database.
     *
     * @param path il percorso del database
     */
    public static void setPath(String path) {
        DBConf.path = path;
    }
}
