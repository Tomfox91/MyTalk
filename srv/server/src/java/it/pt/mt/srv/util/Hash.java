/* file:        Hash.java
 * package:     it.pt.mt.srv.util
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-10  TF  Prima stesura
 */
package it.pt.mt.srv.util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * La classe gestisce lo hash con cui vengono memorizzate le connessioni.
 */
public class Hash {

    private byte[] hash;

    /**
     * Crea lo hash. Crea lo hash della stringa in input
     * usando MD5
     *
     * @param s String
     */
    public Hash(String s) {
        try {
            hash = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
        } catch (Exception ex) {
            // non succede
        }
    }

    /**
     * Restituisce lo hash come stringa.
     *
     * @return restituisce lo hash come stringa
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Restituisce lo hash.
     *
     * @return restituisce lo hash come array di Byte.
     */
    public byte[] getHash() {
        return hash.clone();
    }

    /**
     * Controlla che lo hash inserito sia uguale all'hash
     * memorizzato.
     *
     * @param obj
     * @return restituisce true se e solo se lo hash è uguale allo
     * hash memorizzato
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hash) {
            return (Arrays.equals(hash, ((Hash) obj).getHash()));
        } else {
            return false;
        }
    }

    /**
     * Restituisce i primi 4 Byte dello hash.
     *
     * @return restituisce i primi 4 Byte dello hash.
     */
    @Override
    public int hashCode() {
        ByteBuffer b = ByteBuffer.wrap(hash, 0, 4);
        return b.getInt();
    }

    /**
     * Restituisce l'ultimo Byte dello hash.
     *
     * @return restituisce l'ultimo Byte dello hash
     */
    public byte ultimoByte() {
        return hash[15];
    }
}
