/* file:        UtenteRegistrato.java
 * package:     it.pt.mt.srv.model
 * progetto:    server
 * versione:    1.4
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-12  TF  Prima stesura
 * 1.1  2013-04-08  TF  Rinomino metodi (ex richiestaAmiciziaDa e accettataAmiciziaCon)
 * 1.2  2013-05-13  TF  Aggiungo rifiutaAmiciziaCon
 * 1.3  2013-05-17  TF  Aggiungo cancellaAmiciziaCon
 * 1.4  2013-06-03  TF  Aggiungo metodi hashCode ed equals
 */
package it.pt.mt.srv.model;

import com.spoledge.audao.db.dao.DaoException;
import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.model.db.dto.Amicizia;
import it.pt.mt.srv.model.db.dto.Messaggio;
import it.pt.mt.srv.model.db.dto.RichiestaAmicizia;
import it.pt.mt.srv.model.db.dto.Utente;
import it.pt.mt.srv.util.ConditionException;
import it.pt.mt.srv.util.Password;
import java.util.ArrayList;
import java.util.List;
import static it.pt.mt.srv.util.CheckArg.*;
import java.util.Arrays;

/**
 * Rappresenta un utente autenticato
 */
public class UtenteRegistrato implements IUtenteRegistrato {

    private final long userid;

    /**
     * Crea l'oggetto a partire dal suo id.
     *
     * @param userid l'id dello specifico utente nel database
     */
    public UtenteRegistrato(long userid) {
        this.userid = userid;
    }

    @Override
    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    public boolean equals(Object obj) {
        return (obj instanceof UtenteRegistrato && ((UtenteRegistrato)obj).userid == userid);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (int) (this.userid ^ (this.userid >>> 32));
        return hash;
    }

    /**
     * Recupera i dati dell'utente dal database.
     *
     * @param userid l'id dello specifico utente nel database
     */
    private Utente recuperaUtente() {
        Utente u = RegistroDao.getInstance().getUtenteDao().findById(userid);
        assertArg(u != null);
        return u;
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return la stringa dell'username dell'utente
     */
    @Override
    public String getUserName() {
        return recuperaUtente().getUsername();
    }

    /**
     * Restituisce lo stato dell'utente.
     *
     * @return la stringa che rappresenta lo stato dell'utente
     */
    @Override
    public String getStato() {
        return recuperaUtente().getStato();
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return la stringa dell'email dell'utente
     */
    @Override
    public String getEmail() {
        return recuperaUtente().getEmail();
    }

    /**
     * Restituisce la domanda di sicurezza associata all'utente.
     *
     * @return Restituisce la stringa che rappresenta la domanda segreta
     * dell'utente
     */
    @Override
    public String getDomanda() {
        return recuperaUtente().getDomanda();
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return Restituisce la stringa che rappresenta l'email dell'utente
     */
    @Override
    public String getNome() {
        return getEmail();
    }

    /**
     * Restituisce la lista di amici dell'utente.
     *
     * @return un array di con gli id degli amici dell'utente
     */
    @Override
    public List<IUtenteRegistrato> getAmici() {
        Amicizia aamici[] = RegistroDao.getInstance().getAmiciziaDao().findByPrincipale(userid);
        ArrayList<IUtenteRegistrato> amici =
                new ArrayList<IUtenteRegistrato>(aamici.length);
        for (int i = 0; i < aamici.length; i++) {
            amici.add(new UtenteRegistrato(aamici[i].getAltro()));
        }
        return amici;
    }

    /**
     * Restituisce le amicizie attualmente pendenti.
     *
     * @return un array di id di coloro che hanno una amicizia pendente
     * con l'utente
     */
    @Override
    public List<IUtenteRegistrato> getAmiciziePendenti() {
        RichiestaAmicizia aamici[] = RegistroDao.getInstance()
                .getRichiestaAmiciziaDao().findByDestinatario(userid);
        ArrayList<IUtenteRegistrato> amici =
                new ArrayList<IUtenteRegistrato>(aamici.length);
        for (int i = 0; i < aamici.length; i++) {
            amici.add(new UtenteRegistrato(aamici[i].getMittente()));
        }
        return amici;
    }

    /**
     * Genera una nuova istanza della classe messaggio a partire dal testo e il
     * mittente.
     *
     * @param emailMittente l'email dell'utente che invia il messaggio
     * @param messaggio il testo del messaggio
     * @throws ConditionException se l'amico non esiste
     */
    @Override
    public void nuovoMessaggio(String emailMittente, String messaggio)
            throws ConditionException {
        Utente amico = RegistroDao.getInstance().getUtenteDao()
                .findByEmail(emailMittente);
        checkArg(amico != null);
        long altroid = amico.getId();

        Messaggio mess = new it.pt.mt.srv.model.db.dto.Messaggio();
        mess.setDestinatario(userid);
        mess.setMittente(altroid);
        mess.setMessaggio(messaggio);
        try {
            RegistroDao.getInstance().getMessaggioDao().insert(mess);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }
    }

    /**
     * Restituisce i messaggi per l'utente e li rimuove dalla base di dati.
     *
     * @return una array dei messaggi per l'utente.
     */
    @Override
    public List<IMessaggio> getMessaggi() {
        Messaggio[] messaggi = RegistroDao.getInstance()
                .getMessaggioDao().findByDestinatario(userid);

        List<IMessaggio> ret = new ArrayList<IMessaggio>(messaggi.length);
        for (int i = 0; i < messaggi.length; i++) {
            EMessaggio m = new EMessaggio();
            m.setMessaggio(messaggi[i].getMessaggio());
            String email = RegistroDao.getInstance().getUtenteDao()
                    .findById(messaggi[i].getMittente()).getEmail();
            m.setMittente(email);
            ret.add(m);
        }

        try {
            RegistroDao.getInstance().getMessaggioDao().deleteByDestinatario(userid);
        } catch (DaoException ex) {
            //non succede
        }

        return ret;
    }

    /**
     * Imposta lo username dell'utente.
     *
     * @param username la stringa che rappresenta lo username
     * @throws ConditionException se la stringa dello username non è
     * accettata dal database
     */
    @Override
    public void setUserName(String username) throws ConditionException {
        try {
            RegistroDao.getInstance().getUtenteDao().updateUsername(userid, username);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }
    }

    /**
     * Imposta lo stato dell'utente.
     *
     * @param stato la stringa che rappresenta lo stato
     * @throws ConditionException se la stringa che rappresenta lo stato non è
     * accettata dal database
     */
    @Override
    public void setStato(String stato) throws ConditionException {
        try {
            RegistroDao.getInstance().getUtenteDao().updateStato(userid, stato);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }
    }

    /**
     * La classe gestiosce la richiesta di una amicizia. Viene invocata da chi
     * riceve la richiesta
     *
     * @param emailAmico l'email di chi compie la richiesta
     * @throws ConditionException se l'email dell'amico non è corretta
     */
    @Override
    public void registraRichiestaAmiciziaDa(String emailAmico)
            throws ConditionException {
        checkArg(!emailAmico.equals(getEmail()));
        Utente amico = RegistroDao.getInstance()
                .getUtenteDao().findByEmail(emailAmico);
        checkArg(amico != null);

        RichiestaAmicizia ra = new RichiestaAmicizia();
        ra.setDestinatario(userid);
        ra.setMittente(amico.getId());
        try {
            RegistroDao.getInstance().getRichiestaAmiciziaDao().insert(ra);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }
    }

    /**
     * La funzione imposta una amicizia che l'utente ha richiesto come
     * accettata. Viene invocata sull'utente che ha fatto la richiesta.
     *
     * @param emailAmico l'email dell'amico a cui l'amicizia è stata
     * chiesta
     * @throws ConditionException se l'amico non esiste
     */
    @Override
    public void accettaAmiciziaCon(String emailAmico)
            throws ConditionException {
        checkArg(!emailAmico.equals(getEmail()));
        Utente amico = RegistroDao.getInstance()
                .getUtenteDao().findByEmail(emailAmico);
        checkArg(amico != null);
        long altroid = amico.getId();
        long ida = Math.min(userid, altroid);
        long idb = Math.max(userid, altroid);

        RichiestaAmicizia ra = RegistroDao.getInstance().getRichiestaAmiciziaDao()
                .findByMittenteDestinatario(userid, amico.getId());
        checkArg(ra != null);

        try {
            boolean ok;
            ok = RegistroDao.getInstance().getRichiestaAmiciziaDao()
                    .deleteByMittenteDestinatario(userid, amico.getId());
            checkArg(ok);

            Amicizia a = new Amicizia();
            Amicizia b = new Amicizia();
            a.setPrincipale(ida);
            a.setAltro(idb);
            b.setPrincipale(idb);
            b.setAltro(ida);

            RegistroDao.getInstance().getAmiciziaDao()
                    .insertAll(Arrays.asList(a, b));
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }

    }

    /**
     * La funzione imposta una amicizia che l'utente ha richiesto come
     * rifiutata. Viene invocata sull'utente che ha fatto la richiesta.
     *
     * @param emailAmico l'email dell'amico a cui l'amicizia è stata
     * chiesta
     * @throws ConditionException se l'amico non esiste
     */
    @Override
    public void rifiutaAmiciziaCon(String emailAmico)
            throws ConditionException {
        checkArg(!emailAmico.equals(getEmail()));
        Utente amico = RegistroDao.getInstance()
                .getUtenteDao().findByEmail(emailAmico);
        checkArg(amico != null);
        long altroid = amico.getId();

        RichiestaAmicizia ra = RegistroDao.getInstance().getRichiestaAmiciziaDao()
                .findByMittenteDestinatario(userid, amico.getId());
        checkArg(ra != null);

        try {
            boolean ok;
            ok = RegistroDao.getInstance().getRichiestaAmiciziaDao()
                    .deleteByMittenteDestinatario(userid, amico.getId());
            checkArg(ok);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }

    }

    /**
     * Rimuove un'amicizia.
     *
     * @param emailExAmico l'email dell'amico da rimuovere
     * @throws ConditionException se l'amico non esiste
     */
    @Override
    public void cancellaAmiciziaCon(String emailExAmico) throws ConditionException {
        checkArg(!emailExAmico.equals(getEmail()));
        Utente amico = RegistroDao.getInstance()
                .getUtenteDao().findByEmail(emailExAmico);
        checkArg(amico != null);
        long altroid = amico.getId();

        try {
            int res = RegistroDao.getInstance().getAmiciziaDao()
                    .deleteCoppiaAmicizie(userid, altroid);
            checkArg(res == 2);
        } catch (DaoException ex) {
            throw new ConditionException(ex);
        }
    }

    /**
     * Controlla che inviata sia la password corretta.
     *
     * @param password la stringa che rappresenta la password del'utente
     * @return true se la password inviata è quella corretta
     */
    @Override
    public boolean checkPassword(String password) {
        String vera = recuperaUtente().getPassword();
        return Password.checkPassword(password, vera);
    }

    /**
     * Cambia la password preesistente con quella nuova indicata.
     *
     * @param vecchia stringa che rappresenta la password preesistente
     * @param nuova stirnga che rappresenta la nuova password da impostare
     * per l'utente
     * @return true se la password è stata cambiata
     */
    @Override
    public boolean cambiaPassword(String vecchia, String nuova) {
        if (!checkPassword(vecchia)) {
            return false;
        }
        String nuovohash = Password.hashPassword(nuova);
        try {
            return RegistroDao.getInstance().getUtenteDao()
                    .updatePassword(userid, nuovohash);
        } catch (DaoException ex) {
            return false;
        }
    }

    /**
     * Cambia la password dell'utente utilizzando la domanda segreta.
     *
     * @param risposta è la stringa contenente la risposta alla domanda segreta
     * @param nuovaPassword la nuova password dell'utente
     * @return true se la password è stata cambiata
     */
    @Override
    public boolean cambiaPasswordConRisposta(String risposta, String nuovaPassword) {
        String rispostavera = recuperaUtente().getRisposta();
        if (!Password.checkPassword(risposta, rispostavera)) {
            return false;
        }

        String nuovohash = Password.hashPassword(nuovaPassword);
        try {
            return RegistroDao.getInstance().getUtenteDao()
                    .updatePassword(userid, nuovohash);
        } catch (DaoException ex) {
            return false;
        }
    }
}
