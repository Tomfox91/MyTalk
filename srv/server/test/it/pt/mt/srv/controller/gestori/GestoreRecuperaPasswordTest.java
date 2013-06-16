package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class})
public class GestoreRecuperaPasswordTest {

    /**
     * Test of esegui method, of class GestoreRecuperaPassword.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.cambiaPasswordConRisposta("risposta", "nuova"))
                .thenReturn(Boolean.TRUE);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);

        GestoreRecuperaPassword g = new GestoreRecuperaPassword();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        g.setMittente(c);
        g.setRisposta("risposta");
        g.setNuova("nuova");
        g.setEmail("a@b.com");
        g.esegui();

        verify(n).setOriginale("RecuperaPassword");
        verify(n).setRiuscita(true);
        verify(c).send("not");
    }

    /**
     * Test of esegui method, of class GestoreRecuperaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        IConnessione c = mock(IConnessione.class);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com"))
                .thenThrow(new ConditionException());

        GestoreRecuperaPassword g = new GestoreRecuperaPassword();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setMittente(c);
        g.setRisposta("risposta");
        g.setNuova("nuova");
        g.setEmail("a@b.com");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRecuperaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        IConnessione c = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.cambiaPasswordConRisposta("risposta", "nuova"))
                .thenReturn(Boolean.FALSE);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        GestoreRecuperaPassword g = new GestoreRecuperaPassword();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setMittente(c);
        g.setRisposta("risposta");
        g.setNuova("nuova");
        g.setEmail("a@b.com");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRecuperaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr2() throws Exception {
        GestoreRecuperaPassword g = new GestoreRecuperaPassword();
        //manca risposta
        g.setNuova("nuova");
        g.setEmail("a@b.com");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRecuperaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr3() throws Exception {
        GestoreRecuperaPassword g = new GestoreRecuperaPassword();
        g.setRisposta("risposta");
        //manca nuova
        g.setEmail("a@b.com");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreRecuperaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr4() throws Exception {
        GestoreRecuperaPassword g = new GestoreRecuperaPassword();
        g.setRisposta("risposta");
        g.setNuova("nuova");
        //manca email
        g.esegui();
    }
}