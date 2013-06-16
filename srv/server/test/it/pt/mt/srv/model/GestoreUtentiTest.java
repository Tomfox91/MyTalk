package it.pt.mt.srv.model;

import com.spoledge.audao.db.dao.DaoException;
import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.model.db.dao.UtenteDao;
import it.pt.mt.srv.model.db.dto.Utente;
import it.pt.mt.srv.util.ConditionException;
import it.pt.mt.srv.util.Password;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RegistroDao.class)
public class GestoreUtentiTest {

    /**
     * Test of getInstance method, of class GestoreUtenti.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(GestoreUtenti.getInstance());
    }

    /**
     * Test of getUtenteIP method, of class GestoreUtenti.
     */
    @Test
    public void testGetUtenteIP() throws ConditionException {
        assertEquals("1.2.3.4",
                GestoreUtenti.getInstance().getUtenteIP("1.2.3.4").getIP());
    }

    /**
     * Test of getUtenteIP method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testGetUtenteIPErr() throws ConditionException {
        GestoreUtenti.getInstance().getUtenteIP(null);
    }

    /**
     * Test of getUtenteRegistrato method, of class GestoreUtenti.
     */
    @Test
    public void testGetUtenteRegistrato() throws Exception {
        GestoreUtenti instance = GestoreUtenti.getInstance();

        Utente u = mock(Utente.class);
        when(u.getId()).thenReturn(42l);

        UtenteDao dao = mock(UtenteDao.class);
        when(dao.findByEmail("hit@mail.com")).thenReturn(u);
        RegistroDao Rdao = mock(RegistroDao.class);
        when(Rdao.getUtenteDao()).thenReturn(dao);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(Rdao);

        UtenteRegistrato ur = (UtenteRegistrato) instance.getUtenteRegistrato("hit@mail.com");
        assertEquals(42l, Whitebox.getInternalState(ur, "userid"));
    }

    /**
     * Test of getUtenteRegistrato method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testGetUtenteRegistratoErr1() throws Exception {
        GestoreUtenti.getInstance().getUtenteRegistrato(null);
    }

    /**
     * Test of getUtenteRegistrato method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testGetUtenteRegistratoErr2() throws Exception {
        GestoreUtenti instance = GestoreUtenti.getInstance();

        UtenteDao dao = mock(UtenteDao.class);
        RegistroDao Rdao = mock(RegistroDao.class);
        when(Rdao.getUtenteDao()).thenReturn(dao);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(Rdao);

        instance.getUtenteRegistrato("miss@mail.com");
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtenteNoEmail() throws Exception {
        GestoreUtenti.getInstance().nuovoUtente(null, "u", "p", "d", "r");
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtenteNoUser() throws Exception {
        GestoreUtenti.getInstance().nuovoUtente("email@dominio.it", null, "p",
                "d", "r");
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtenteNoPass() throws Exception {
        GestoreUtenti.getInstance().nuovoUtente("email@dominio.it", "u", null,
                "d", "r");
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtenteNoDom() throws Exception {
        GestoreUtenti.getInstance().nuovoUtente("email@dominio.it", "u", "p",
                null, "r");
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtenteNoRisp() throws Exception {
        GestoreUtenti.getInstance().nuovoUtente("email@dominio.it", "u", "p",
                "d", null);
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtenteEmailSbagliata() throws Exception {
        GestoreUtenti.getInstance().nuovoUtente("email@domi@nio.it", "u", "p",
                "d", "r");
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test
    public void testNuovoUtente() throws Exception {
        final List<Utente> l = new ArrayList<Utente>();

        Answer<Long> ans1 = new Answer<Long>() {
            @Override
            public Long answer(InvocationOnMock invocation) throws Throwable {
                Utente u = (Utente) invocation.getArguments()[0];
                u.setId(42l);
                l.add(u);
                return 42l;
            }
        };

        Answer<Utente> ans2 = new Answer<Utente>() {
            @Override
            public Utente answer(InvocationOnMock invocation) throws Throwable {
                return l.get(0);
            }
        };

        UtenteDao dao = mock(UtenteDao.class);
        when(dao.insert(any(Utente.class))).thenAnswer(ans1);
        when(dao.findByEmail("email@dominio.it")).thenAnswer(ans2);

        RegistroDao Rdao = mock(RegistroDao.class);
        when(Rdao.getUtenteDao()).thenReturn(dao);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(Rdao);

        UtenteRegistrato ur = (UtenteRegistrato) GestoreUtenti.getInstance()
                .nuovoUtente("email@dominio.it", "u", "p", "d", "r");

        assertNotNull(ur);
        Utente u = l.get(0);
        assertNotNull(u);
        assertEquals("email@dominio.it", u.getEmail());
        assertEquals("u", u.getUsername());
        assertEquals("d", u.getDomanda());
        assertTrue(Password.checkPassword("p", u.getPassword()));
        assertTrue(Password.checkPassword("r", u.getRisposta()));
    }

    /**
     * Test of nuovoUtente method, of class GestoreUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoUtente2() throws Exception {
        UtenteDao dao = mock(UtenteDao.class);
        when(dao.insert(any(Utente.class))).thenThrow(new DaoException(""));

        RegistroDao Rdao = mock(RegistroDao.class);
        when(Rdao.getUtenteDao()).thenReturn(dao);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(Rdao);

        GestoreUtenti.getInstance()
                .nuovoUtente("email@dominio.it", "u", "p", "d", "r");
    }

    /**
     * Test of ricercaUtenti method, of class GestoreUtenti.
     */
    @Test
    public void testRicercaUtenti() throws ConditionException {
        Utente u = mock(Utente.class);
        when(u.getId()).thenReturn(42l);
        Utente[] au = new Utente[1];
        au[0] = u;

        UtenteDao dao = mock(UtenteDao.class);
        when(dao.findByPattern("%#user%")).thenReturn(au);

        RegistroDao Rdao = mock(RegistroDao.class);
        when(Rdao.getUtenteDao()).thenReturn(dao);
        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(Rdao);

        List<IUtenteRegistrato> risultato =
                GestoreUtenti.getInstance().ricercaUtenti("uSeR");

        assertEquals(1, risultato.size());
        assertEquals(42l, Whitebox.getInternalState(risultato.get(0), "userid"));
    }
}
