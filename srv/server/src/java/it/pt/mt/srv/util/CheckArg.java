/* file:        CheckArg.java
 * package:     it.pt.mt.srv.util
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-12  TF  Prima stesura
 */
package it.pt.mt.srv.util;

/**
 * La classe gestisce il controllo di un argomento lanciando un
 * ConditionException se è nullo .
 */
public class CheckArg {

    public static void checkArg(boolean c) throws ConditionException {
        if (!c) {
            throw new ConditionException();
        }
    }

    public static void assertArg(boolean c) throws AssertionError {
        if (!c) {
            throw new AssertionError();
        }
    }
}
