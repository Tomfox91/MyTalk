/* file:        ServerListener.java
 * package:     it.pt.mt.srv.controller.network
 * progetto:    server
 * versione:    1.0
 *
 * Copyright (c) 2013 Polaris Team
 *
 * Modifiche:
 * vers data        aut descrizione
 * 1.0  2013-03-18  TF  Prima stesura
 */
package it.pt.mt.srv.controller.network;

import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.util.DBConf;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Controlla la connessione con il server Tomcat.
 */
public class ServerListener implements ServletContextListener {

    /**
     * Il costruttore vuoto
     */
    public ServerListener() {
    }

    /**
     * Il metodo viene chiamato quando la servlet viene caricata.
     *
     * @param sce la servlet da gestire
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getInitParameter("db-path");
        DBConf.setPath(path);
    }

    /**
     * Il metodo viene chiamato quando la servlet viene chiuso.
     *
     * @param sce la servlet da gestire
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RegistroDao.getInstance().shutdownDB();
    }
}
