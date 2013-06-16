package it.pt.mt.srv.model;

import com.spoledge.audao.db.dao.DaoException;
import it.pt.mt.srv.model.db.RegistroDao;
import it.pt.mt.srv.model.db.dao.AmiciziaDao;
import it.pt.mt.srv.model.db.dao.MessaggioDao;
import it.pt.mt.srv.model.db.dao.RichiestaAmiciziaDao;
import it.pt.mt.srv.model.db.dao.UtenteDao;
import it.pt.mt.srv.model.db.dto.Amicizia;
import it.pt.mt.srv.model.db.dto.Messaggio;
import it.pt.mt.srv.model.db.dto.RichiestaAmicizia;
import it.pt.mt.srv.model.db.dto.Utente;
import it.pt.mt.srv.util.ConditionException;
import it.pt.mt.srv.util.Password;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RegistroDao.class)
public class UtenteRegistratoTest {

    private final static String domanda = "domanda?";
    private final static String email = "esempio@dominio.it";
    private final static String altraemail = "altroesempio@dominio.it";
    private final static long id = 42;
    private final static String vpass = "ciao";
    private final static String pass = Password.hashPassword(vpass);
    private final static String vrisp = "ciao";
    private final static String risp = Password.hashPassword(vrisp);
    private final static String stato = "stato";
    private final static String un = "username";
    private final static String mess = "coso";
    private static UtenteDao udao;
    private static AmiciziaDao adao;
    private static RichiestaAmiciziaDao rdao;
    private static MessaggioDao mdao;

    @Before
    public void setUp() throws Exception {
        Utente u = mock(Utente.class);
        when(u.getDomanda()).thenReturn(domanda);
        when(u.getEmail()).thenReturn(email);
        when(u.getId()).thenReturn(id);
        when(u.getPassword()).thenReturn(pass);
        when(u.getRisposta()).thenReturn(risp);
        when(u.getStato()).thenReturn(stato);
        when(u.getUsername()).thenReturn(un);

        Utente u2 = mock(Utente.class);
        when(u2.getDomanda()).thenReturn(domanda);
        when(u2.getEmail()).thenReturn(altraemail);
        when(u2.getId()).thenReturn(id + 1);
        when(u2.getPassword()).thenReturn(pass);
        when(u2.getRisposta()).thenReturn(risp);
        when(u2.getStato()).thenReturn(stato);
        when(u2.getUsername()).thenReturn(un);

        udao = mock(UtenteDao.class);
        when(udao.findById(id)).thenReturn(u);
        when(udao.findById(id + 1)).thenReturn(u2);
        when(udao.findByEmail(email)).thenReturn(u);
        when(udao.findByEmail(altraemail)).thenReturn(u2);
        when(udao.updatePassword(eq(id), anyString())).thenReturn(Boolean.TRUE);

        Amicizia a = mock(Amicizia.class);
        when(a.getPrincipale()).thenReturn(id);
        when(a.getAltro()).thenReturn(id + 1);

        adao = mock(AmiciziaDao.class);
        Amicizia[] l = {a};
        when(adao.findByPrincipale(id)).thenReturn(l);

        RichiestaAmicizia ra = mock(RichiestaAmicizia.class);
        when(ra.getMittente()).thenReturn(id + 1);
        when(ra.getDestinatario()).thenReturn(id);
        RichiestaAmicizia[] rarr = new RichiestaAmicizia[1];
        rarr[0] = ra;

        rdao = mock(RichiestaAmiciziaDao.class);
        when(rdao.findByMittenteDestinatario(id, id + 1)).thenReturn(ra);
        when(rdao.deleteByMittenteDestinatario(id, id + 1)).thenReturn(Boolean.TRUE);
        when(rdao.findByDestinatario(id)).thenReturn(rarr);

        Messaggio m = mock(Messaggio.class);
        when(m.getDestinatario()).thenReturn(id);
        when(m.getMittente()).thenReturn(id + 1);
        when(m.getMessaggio()).thenReturn(mess);
        mdao = mock(MessaggioDao.class);
        Messaggio[] marr = new Messaggio[1];
        marr[0] = m;
        when(mdao.findByDestinatario(id)).thenReturn(marr);

        RegistroDao Rdao = mock(RegistroDao.class);
        when(Rdao.getAmiciziaDao()).thenReturn(adao);
        when(Rdao.getMessaggioDao()).thenReturn(mdao);
        when(Rdao.getRichiestaAmiciziaDao()).thenReturn(rdao);
        when(Rdao.getUtenteDao()).thenReturn(udao);

        mockStatic(RegistroDao.class);
        when(RegistroDao.getInstance()).thenReturn(Rdao);
    }

    /**
     * Test of constructor of class UtenteRegistrato.
     */
    @Test(expected = AssertionError.class)
    public void testConstructor() {
        new UtenteRegistrato(-1).getNome();
    }

    /**
     * Test of getUserName method, of class UtenteRegistrato.
     */
    @Test
    public void testGetUserName() {
        assertEquals(
                un,
                new UtenteRegistrato(id).getUserName());
    }

    /**
     * Test of equals & hashCode method, of class UtenteRegistrato.
     */
    @Test
    public void testEquals() {
        assertTrue(new UtenteRegistrato(id).equals(new UtenteRegistrato(id)));
        assertFalse(new UtenteRegistrato(id).equals(new UtenteRegistrato(id + 1)));
        assertFalse(new UtenteRegistrato(id).equals(new Object()));
        assertTrue(new UtenteRegistrato(id).hashCode()
                == new UtenteRegistrato(id).hashCode());
    }

    /**
     * Test of getStato method, of class UtenteRegistrato.
     */
    @Test
    public void testGetStato() {
        assertEquals(
                stato,
                new UtenteRegistrato(id).getStato());
    }

    /**
     * Test of getEmail method, of class UtenteRegistrato.
     */
    @Test
    public void testGetEmail() {
        assertEquals(
                email,
                new UtenteRegistrato(id).getEmail());
    }

    /**
     * Test of getDomanda method, of class UtenteRegistrato.
     */
    @Test
    public void testGetDomanda() {
        assertEquals(
                domanda,
                new UtenteRegistrato(id).getDomanda());
    }

    /**
     * Test of getNome method, of class UtenteRegistrato.
     */
    @Test
    public void testGetNome() {
        assertEquals(
                email,
                new UtenteRegistrato(id).getNome());
    }

    /**
     * Test of getAmici method, of class UtenteRegistrato.
     */
    @Test
    public void testGetAmici() {
        List<IUtenteRegistrato> amici = new UtenteRegistrato(id).getAmici();
        assertEquals(1, amici.size());
        assertEquals(altraemail, amici.get(0).getEmail());
    }

    /**
     * Test of getAmiciziePendenti method, of class UtenteRegistrato.
     */
    @Test
    public void testGetAmiciziePendenti() {
        List<IUtenteRegistrato> amiciziePendenti =
                new UtenteRegistrato(id).getAmiciziePendenti();
        assertEquals(1, amiciziePendenti.size());
        assertEquals(altraemail, amiciziePendenti.get(0).getEmail());
    }

    /**
     * Test of nuovoMessaggio method, of class UtenteRegistrato.
     */
    @Test
    public void testNuovoMessaggio() throws Exception {
        new UtenteRegistrato(id).nuovoMessaggio(altraemail, mess);

        Messaggio messaggio = new Messaggio();
        messaggio.setDestinatario(id);
        messaggio.setMittente(id + 1);
        messaggio.setMessaggio(mess);

        verify(mdao).insert(messaggio);
    }

    /**
     * Test of nuovoMessaggio method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testNuovoMessaggioErr() throws Exception {
        new UtenteRegistrato(id).nuovoMessaggio("ine@sisten.te", mess);
    }

    /**
     * Test of getMessaggi method, of class UtenteRegistrato.
     */
    @Test
    public void testGetMessaggi() throws Exception {
        List<IMessaggio> messaggi = new UtenteRegistrato(id).getMessaggi();
        assertEquals(mess, messaggi.get(0).getMessaggio());
        assertEquals(altraemail, messaggi.get(0).getMittente());
        verify(mdao).deleteByDestinatario(id);
    }

    /**
     * Test of setUserName method, of class UtenteRegistrato.
     */
    @Test
    public void testSetUserName() throws Exception {
        new UtenteRegistrato(id).setUserName("egwe4gf");
        verify(udao).updateUsername(id, "egwe4gf");
    }

    /**
     * Test of setStato method, of class UtenteRegistrato.
     */
    @Test
    public void testSetStato() throws Exception {
        new UtenteRegistrato(id).setStato("egwe4gf");
        verify(udao).updateStato(id, "egwe4gf");
    }

    /**
     * Test of registraRichiestaAmiciziaDa method, of class UtenteRegistrato.
     */
    @Test
    public void testRichiestaAmiciziaDa() throws Exception {
        new UtenteRegistrato(id).registraRichiestaAmiciziaDa(altraemail);
        verify(rdao).insert(any(RichiestaAmicizia.class));
    }

    /**
     * Test of registraRichiestaAmiciziaDa method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testRichiestaAmiciziaDa2() throws Exception {
        new UtenteRegistrato(id).registraRichiestaAmiciziaDa("inesi@stente.com");
    }

    /**
     * Test of registraRichiestaAmiciziaDa method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testRichiestaAmiciziaDa3() throws Exception {
        new UtenteRegistrato(id).registraRichiestaAmiciziaDa(email);
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test
    public void testAccettataAmiciziaCon() throws Exception {
        new UtenteRegistrato(id).accettaAmiciziaCon(altraemail);
        verify(rdao).deleteByMittenteDestinatario(id, id + 1);
        verify(adao).insertAll(anyListOf(Amicizia.class));
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testAccettataAmiciziaCon2() throws Exception {
        //altro inesistente
        new UtenteRegistrato(id).accettaAmiciziaCon("inesi@stente.com");
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testAccettataAmiciziaCon3() throws Exception {
        //auto-amicizia
        new UtenteRegistrato(id).accettaAmiciziaCon(email);
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testAccettataAmiciziaCon4() throws Exception {
        reset(rdao);
        //altro esistente che non ha fatto richiesta
        new UtenteRegistrato(id).accettaAmiciziaCon(altraemail);
    }

    /**
     * Test of cancellaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test
    public void testCancellaAmiciziaCon() throws Exception {
        when(adao.deleteCoppiaAmicizie(id, id + 1)).thenReturn(2);
        new UtenteRegistrato(id).cancellaAmiciziaCon(altraemail);
        verify(adao).deleteCoppiaAmicizie(id, id + 1);
    }

    /**
     * Test of cancellaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testCancellaAmiciziaCon0() throws Exception {
        when(adao.deleteCoppiaAmicizie(id, id + 1)).thenReturn(0); //non amici
        new UtenteRegistrato(id).cancellaAmiciziaCon(altraemail);
    }

    /**
     * Test of cancellaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testCancellaAmiciziaCon1() throws Exception {
        //con se stesso
        new UtenteRegistrato(id).cancellaAmiciziaCon(email);
    }

    /**
     * Test of cancellaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testCancellaAmiciziaCon2() throws Exception {
        new UtenteRegistrato(id).cancellaAmiciziaCon("cicci@bu.bu"); //inesistente
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test
    public void testRifiutataAmiciziaCon() throws Exception {
        new UtenteRegistrato(id).rifiutaAmiciziaCon(altraemail);
        verify(rdao).deleteByMittenteDestinatario(id, id + 1);
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testRifiutataAmiciziaCon2() throws Exception {
        //altro inesistente
        new UtenteRegistrato(id).rifiutaAmiciziaCon("inesi@stente.com");
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testRifiutataAmiciziaCon3() throws Exception {
        //auto-amicizia
        new UtenteRegistrato(id).rifiutaAmiciziaCon(email);
    }

    /**
     * Test of accettaAmiciziaCon method, of class UtenteRegistrato.
     */
    @Test(expected = ConditionException.class)
    public void testRifiutataAmiciziaCon4() throws Exception {
        reset(rdao);
        //altro esistente che non ha fatto richiesta
        new UtenteRegistrato(id).rifiutaAmiciziaCon(altraemail);
    }

    /**
     * Test of checkPassword method, of class UtenteRegistrato.
     */
    @Test
    public void testCheckPassword() {
        assertTrue(new UtenteRegistrato(id).checkPassword(vpass));
        assertFalse(new UtenteRegistrato(id).checkPassword("q"));
    }

    /**
     * Test of cambiaPassword method, of class UtenteRegistrato.
     */
    @Test
    public void testCambiaPassword() throws DaoException {
        assertTrue(new UtenteRegistrato(id).cambiaPassword(vpass, "z"));
        assertFalse(new UtenteRegistrato(id).cambiaPassword("q", "z"));
        verify(udao).updatePassword(eq(id), anyString());
    }

    /**
     * Test of cambiaPasswordConRisposta method, of class UtenteRegistrato.
     */
    @Test
    public void testCambiaPasswordConRisposta() throws DaoException {
        assertTrue(new UtenteRegistrato(id).
                cambiaPasswordConRisposta(vrisp, "z"));
        assertFalse(new UtenteRegistrato(id).
                cambiaPasswordConRisposta("q", "z"));
        verify(udao).updatePassword(eq(id), anyString());
    }
}
