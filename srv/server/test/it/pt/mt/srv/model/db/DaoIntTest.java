package it.pt.mt.srv.model.db;

import com.spoledge.audao.db.dao.DaoException;
import it.pt.mt.srv.model.db.dto.Amicizia;
import it.pt.mt.srv.model.db.dto.Messaggio;
import it.pt.mt.srv.model.db.dto.RichiestaAmicizia;
import it.pt.mt.srv.model.db.dto.Utente;
import it.pt.mt.srv.util.DBConf;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class DaoIntTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
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

    @Test
    public void testDB() throws DaoException {
        RegistroDao r = RegistroDao.getInstance();

        Utente utente = new Utente();
        utente.setEmail("a@b.com");
        utente.setDomanda("d");
        utente.setRisposta("r");
        utente.setPassword("p");
        utente.setUsername("username");
        utente.setStato("s");
        long id = r.getUtenteDao().insert(utente);

        utente = new Utente();
        utente.setEmail("b@c.com");
        utente.setDomanda("d");
        utente.setRisposta("r");
        utente.setPassword("p");
        utente.setUsername("asername");
        utente.setStato("s");
        long altroid = r.getUtenteDao().insert(utente);

        utente = r.getUtenteDao().findByEmail("a@b.com");
        assertNotNull(utente);
        assertEquals("a@b.com", utente.getEmail());
        assertEquals("d", utente.getDomanda());
        assertEquals("r", utente.getRisposta());
        assertEquals("p", utente.getPassword());
        assertEquals("username", utente.getUsername());
        assertEquals("s", utente.getStato());

        Utente[] findByPattern = r.getUtenteDao().findByPattern("%#user%");
        assertEquals(1, findByPattern.length);
        assertEquals(utente, findByPattern[0]);
        findByPattern = r.getUtenteDao().findByPattern("%#a@b%");
        assertEquals(1, findByPattern.length);
        assertEquals(utente, findByPattern[0]);

        Amicizia amicizia = new Amicizia();
        amicizia.setPrincipale(id);
        amicizia.setAltro(altroid);
        Amicizia amicizib = new Amicizia();
        amicizib.setPrincipale(altroid);
        amicizib.setAltro(id);
        r.getAmiciziaDao().insertAll(Arrays.asList(amicizia, amicizib));
        assertNotNull(r.getAmiciziaDao().findByPrincipaleAltro(id, altroid));
        assertNotNull(r.getAmiciziaDao().findByPrincipaleAltro(altroid, id));

        assertEquals(2, r.getAmiciziaDao().deleteCoppiaAmicizie(id, altroid));
        assertNull(r.getAmiciziaDao().findByPrincipaleAltro(id, altroid));
        assertNull(r.getAmiciziaDao().findByPrincipaleAltro(altroid, id));

        RichiestaAmicizia richiestaAmicizia = new RichiestaAmicizia();
        richiestaAmicizia.setDestinatario(id);
        richiestaAmicizia.setMittente(id);
        r.getRichiestaAmiciziaDao().insert(richiestaAmicizia);
        assertNotNull(r.getRichiestaAmiciziaDao().findByMittenteDestinatario(id, id));

        Messaggio messaggio = new Messaggio();
        messaggio.setDestinatario(id);
        messaggio.setMittente(id);
        messaggio.setMessaggio("42");
        r.getMessaggioDao().insert(messaggio);
        messaggio = (r.getMessaggioDao().findByDestinatario(id))[0];
        assertNotNull(messaggio);
        assertEquals("42", messaggio.getMessaggio());
    }
}
