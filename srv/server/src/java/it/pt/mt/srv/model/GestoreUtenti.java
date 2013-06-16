/* file:        GestoreUtenti.java
 * package:     it.pt.mt.srv.model
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-12  TF  Prima stesura
 */
package it.pt.mt.srv.model;

import com.spoledge.audao.db.dao.DaoException;
import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.model.db.dto.Utente;
import static it.pt.mt.srv.util.CheckArg.checkArg;
import it.pt.mt.srv.util.ConditionException;
import it.pt.mt.srv.util.Password;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce tutte le operazioni sugli utenti. Recupera, crea e ricerca gli utenti.
 */
public class GestoreUtenti {

    private static GestoreUtenti instance;

    /**
     * Il metodo fornisce l'istanza del GestoreUtenti.
     *
     * @return l'istanza del GestoreUtenti
     */
    public static GestoreUtenti getInstance() {
        if (instance == null) {
            instance = new GestoreUtenti();
        }
        return instance;
    }

    /**
     * Costruttore vuoto e privato.
     */
    private GestoreUtenti() {
    }

    /**
     * Restituisce la rappresentazione dell'utente qualora non sia autenticato.
     *
     * @param IP l'indirizzo ip dell'utente
     * @return la rappresentazione di un utente non autenticato
     * @throws ConditionException se la stringa non è un ip
     */
    public IUtenteIP getUtenteIP(String IP) throws ConditionException {
        checkArg(IP != null);
        return new UtenteIP(IP);
    }

    /**
     * Restituisce la rappresentazione dell'utente che si sta autenticando.
     *
     * @param email l'email che identifica l'utente
     * @return la rappresentazione di un utente autenticato
     * @throws ConditionException se l'email non è una presente nel
     * database
     */
    public IUtenteRegistrato getUtenteRegistrato(String email)
            throws ConditionException {
        checkArg(email != null);
        Utente u = RegistroDao.getInstance().getUtenteDao().findByEmail(email);
        checkArg(u != null);

        return new UtenteRegistrato(u.getId());
    }

    /**
     * Crea un nuovo Utente a partire dai dati necessari e lo inserisce nel
     * database.
     *
     * @param email l'email con cui l'utente si autentica
     * @param username lo username scelto per essere identificato
     * @param password la password dell'utente
     * @param domanda la domanda segreta per il recupero password
     * @param risposta La risposta alla domanda segreta
     * @return la rappresentazione dell'utente autenticato
     * @throws ConditionException se si presentano errori nella creazione
     * dell'utente
     */
    public IUtenteRegistrato nuovoUtente(String email, String username,
            String password, String domanda, String risposta)
            throws ConditionException {

        checkArg(email != null);
        checkArg(username != null);
        checkArg(password != null);
        checkArg(domanda != null);
        checkArg(risposta != null);
        checkArg(email.matches("^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$"));

        String passHash = Password.hashPassword(password);
        String rispHash = Password.hashPassword(risposta);

        Utente u = new Utente();
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(passHash);
        u.setDomanda(domanda);
        u.setRisposta(rispHash);

        long id;
        try {
            id = RegistroDao.getInstance().getUtenteDao().insert(u);
            return new UtenteRegistrato(id);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }

    }

    /**
     * Implementa la ricerca degli utenti tramite pattern sugli username.
     *
     * @param pattern la stringa da ricercare negli username
     * @return la lista di utenti che la ricerca restituisce
     * @throws ConditionException se il pattern contiene caratteri non valida
     */
    public List<IUtenteRegistrato> ricercaUtenti(String pattern)
            throws ConditionException {

        checkArg(pattern.matches("[a-zA-Z0-9 @_.-]{2,50}"));

        Utente[] arr = RegistroDao.getInstance().getUtenteDao()
                .findByPattern("%#" + pattern.toLowerCase() + "%");
        ArrayList<IUtenteRegistrato> utenti =
                new ArrayList<IUtenteRegistrato>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            utenti.add(new UtenteRegistrato(arr[i].getId()));
        }
        return utenti;
    }
}
