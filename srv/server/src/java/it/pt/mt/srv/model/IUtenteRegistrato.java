/* file:        IUtenteRegistrato.java
 * package:     it.pt.mt.srv.model
 * progetto:    server
 * versione:    1.3
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-12  TF  Prima stesura
 * 1.1  2013-04-08  TF  Rinomino metodi (ex richiestaAmiciziaDa e accettataAmiciziaCon)
 * 1.2  2013-05-13  TF  Aggiungo rifiutaAmiciziaCon
 * 1.3  2013-05-17  TF  Aggiungo cancellaAmiciziaCon
 */
package it.pt.mt.srv.model;

import it.pt.mt.srv.util.ConditionException;
import java.util.List;

/**
 * Interfaccia che rappresenta un generico utente connesso e autenticato.
 */
public interface IUtenteRegistrato extends IUtente {

    String getUserName();

    String getStato();

    String getEmail();

    String getDomanda();

    List<IUtenteRegistrato> getAmici();

    List<IUtenteRegistrato> getAmiciziePendenti();

    List<IMessaggio> getMessaggi();

    void nuovoMessaggio(String email, String messaggio)
            throws ConditionException;

    void setUserName(String username) throws ConditionException;

    void setStato(String stato) throws ConditionException;

    void registraRichiestaAmiciziaDa(String emailAmico)
            throws ConditionException;

    void accettaAmiciziaCon(String emailAmico)
            throws ConditionException;

    void rifiutaAmiciziaCon(String emailAmico)
            throws ConditionException;

    void cancellaAmiciziaCon(String emailExAmico)
            throws ConditionException;

    boolean checkPassword(String password);

    boolean cambiaPassword(String vecchia, String nuova);

    boolean cambiaPasswordConRisposta(String risposta,
            String nuovaPassword);
}
