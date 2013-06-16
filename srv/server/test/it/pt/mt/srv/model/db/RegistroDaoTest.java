package it.pt.mt.srv.model.db;

import it.pt.mt.srv.util.DBConf;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class RegistroDaoTest {

    @BeforeClass
    public static void setUpDB() throws Exception {
        Process p = Runtime.getRuntime().exec("ant create-tables-audao-hsql-test");
        int res = p.waitFor();
        if (res != 0) {
            System.exit(res);
        }
        DBConf.setPath("db/testdb");
    }

    @AfterClass
    public static void tearDownClass() {
        RegistroDao.getInstance().shutdownDB();
    }

    /**
     * Test of getAmiciziaDao method, of class RegistroDao.
     */
    @Test
    public void testGetAmiciziaDao() {
        assertNotNull(RegistroDao.getInstance().getAmiciziaDao());
    }

    /**
     * Test of getRichiestaAmiciziaDao method, of class RegistroDao.
     */
    @Test
    public void testGetRichiestaAmiciziaDao() {
        assertNotNull(RegistroDao.getInstance().getRichiestaAmiciziaDao());
    }

    /**
     * Test of getMessaggioDao method, of class RegistroDao.
     */
    @Test
    public void testGetMessaggioDao() {
        assertNotNull(RegistroDao.getInstance().getMessaggioDao());
    }

    /**
     * Test of getUtenteDao method, of class RegistroDao.
     */
    @Test
    public void testGetUtenteDao() {
        assertNotNull(RegistroDao.getInstance().getUtenteDao());
    }
}
