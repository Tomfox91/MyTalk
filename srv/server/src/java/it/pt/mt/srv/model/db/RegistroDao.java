/* file:        RegistroDao.java
 * package:     it.pt.mt.srv.model.db
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-11  TF  Prima stesura
 */
package it.pt.mt.srv.model.db;

import it.pt.mt.srv.model.db.dao.AmiciziaDao;
import it.pt.mt.srv.model.db.dao.DaoFactory;
import it.pt.mt.srv.model.db.dao.MessaggioDao;
import it.pt.mt.srv.model.db.dao.RichiestaAmiciziaDao;
import it.pt.mt.srv.model.db.dao.UtenteDao;
import it.pt.mt.srv.util.DBConf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Istanzia e mantiene i riferimenti ai Dao per le varie entità.
 */
public class RegistroDao {

    private static RegistroDao instance;
    private AmiciziaDao amiciziaDao;
    private RichiestaAmiciziaDao richiestaAmiciziaDao;
    private MessaggioDao messaggioDao;
    private UtenteDao utenteDao;
    private Connection connection;

    /**
     * Costruisce l'oggetto del RegistroDao. Crea la connessione al
     * database e istanzia i quattro Dao.
     */
    private RegistroDao() {
        String path = DBConf.getPath();

        Logger.getLogger("mytalk")
                .log(Level.INFO, "Cerco di accedere al db: {0}", path);
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:file:" + path);

            amiciziaDao = DaoFactory.createAmiciziaDao(connection);
            richiestaAmiciziaDao =
                    DaoFactory.createRichiestaAmiciziaDao(connection);
            messaggioDao = DaoFactory.createMessaggioDao(connection);
            utenteDao = DaoFactory.createUtenteDao(connection);

            Logger.getLogger("mytalk")
                    .log(Level.INFO, "Ottenuto l'accesso al db");

            utenteDao.updateTuttiOffline();

            Logger.getLogger("mytalk")
                    .log(Level.INFO, "Impostati tutti gli utenti come offline");

        } catch (Throwable ex) {
            throw new Error("Non riesco ad aprire il db", ex);
        }
    }

    /**
     * Il metodo fornisce l'istanza del RegistoDao.
     *
     * @return l'istanza del RegistroDao
     */
    public static RegistroDao getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new RegistroDao();
            return instance;
        }
    }

    /**
     * Restituisce il riferimento al Dao specifico.
     *
     * @return il Dao per l'entità Amicizia
     */
    public AmiciziaDao getAmiciziaDao() {
        return amiciziaDao;
    }

    /**
     * Restituisce il riferimento al Dao specifico.
     *
     * @return il Dao per l'entità richiesta amicizia
     */
    public RichiestaAmiciziaDao getRichiestaAmiciziaDao() {
        return richiestaAmiciziaDao;
    }

    /**
     * Restituisce il riferimento al Dao specifico.
     *
     * @return il Dao per l'entità messaggi
     */
    public MessaggioDao getMessaggioDao() {
        return messaggioDao;
    }

    /**
     * Restituisce il riferimento al Dao specifico.
     *
     * @return il Dao per l'entità utente
     */
    public UtenteDao getUtenteDao() {
        return utenteDao;
    }

    /**
     * Chiude il database.
     */
    public void shutdownDB() {
        try {
            connection.createStatement().execute("SHUTDOWN");
            Logger.getLogger("mytalk")
                    .log(Level.INFO, "Inviato messaggio di shutdown al db");
        } catch (SQLException ex) {
            //ignoro
        }
        connection = null;
        amiciziaDao = null;
        messaggioDao = null;
        richiestaAmiciziaDao = null;
        utenteDao = null;
        instance = null;
    }
}
