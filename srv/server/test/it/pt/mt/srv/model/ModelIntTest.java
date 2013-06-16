package it.pt.mt.srv.model;

import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.util.DBConf;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class ModelIntTest {

    private static String email = "q@ww.com";
    private static String altemail = "e@ww.com";

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
    public static void tearDownDB() {
        RegistroDao.getInstance().shutdownDB();
    }

    @Test
    public void testDB() throws Exception {
        GestoreUtenti g = GestoreUtenti.getInstance();

        IUtenteRegistrato u = g.nuovoUtente(email, "u1", "pwd", "d", "r");
        assertEquals("u1", g.getUtenteRegistrato(email).getUserName());
        g.getUtenteRegistrato(email).setStato("stato");
        assertEquals("stato", g.getUtenteRegistrato(email).getStato());

        IUtenteRegistrato au = g.nuovoUtente(altemail, "u2", "pwd", "d", "r");
        au.registraRichiestaAmiciziaDa(email);
        assertEquals(email, au.getAmiciziePendenti().get(0).getEmail());
        assertEquals(email, g.ricercaUtenti(email).get(0).getEmail());
        assertEquals(altemail, g.ricercaUtenti("u2").get(0).getEmail());

        u.rifiutaAmiciziaCon(altemail);
        assertEquals(0, u.getAmici().size());
        assertEquals(0, au.getAmiciziePendenti().size());
        assertEquals(0, u.getAmiciziePendenti().size());

        au.registraRichiestaAmiciziaDa(email);

        u.accettaAmiciziaCon(altemail);
        assertEquals(altemail, u.getAmici().get(0).getEmail());
        assertEquals(email, au.getAmici().get(0).getEmail());
        assertEquals(0, au.getAmiciziePendenti().size());
        assertEquals(0, u.getAmiciziePendenti().size());

        u.cancellaAmiciziaCon(altemail);
        assertEquals(0, u.getAmici().size());
        assertEquals(0, au.getAmici().size());

        assertTrue(u.checkPassword("pwd"));
        assertFalse(u.checkPassword("iuwghrghwou"));
        assertTrue(u.cambiaPassword("pwd", "pass"));
        assertTrue(u.checkPassword("pass"));
        assertFalse(u.checkPassword("pwd"));
        assertFalse(u.cambiaPassword("ogwjeoif", "ioghoire"));

        assertTrue(au.cambiaPasswordConRisposta("r", "pass"));
        assertTrue(au.checkPassword("pass"));
        assertFalse(au.checkPassword("pwd"));
        assertFalse(au.cambiaPasswordConRisposta("ogwjeoif", "ioghoire"));

        u.nuovoMessaggio(altemail, "msg");
        assertEquals("msg", u.getMessaggi().get(0).getMessaggio());
        assertEquals(0, u.getMessaggi().size());
    }
}
