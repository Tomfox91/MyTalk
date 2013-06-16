/* file:        Password.java
 * package:     it.pt.mt.srv.util
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-14  TF  Prima stesura
 */
package it.pt.mt.srv.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * La classe fornisce dei metodi di utilità per controllare le password e
 * per salvare le password in maniera sicura.
 */
public class Password {

    private Password() {
    }

    /**
     * Genera lo hash della password.
     * @param inChiaro stringa contenente la password
     * @return Restituisce lo hash della password
     */
    public static String hashPassword(String inChiaro) {
        return BCrypt.hashpw(inChiaro, BCrypt.gensalt());
    }

    /**
     * Controlla che la password inserita sia corretta.
     *
     * @param candidata stringa rappresentante la password inserita
     * dall'utente
     * @param veraHashata string rappresententa la password contenuta nel
     * Database
     * @return Restituisce un vero se e solo se la password inserita è
     * corretta
     */
    public static boolean checkPassword(String candidata, String veraHashata) {
        return BCrypt.checkpw(candidata, veraHashata);
    }
}
