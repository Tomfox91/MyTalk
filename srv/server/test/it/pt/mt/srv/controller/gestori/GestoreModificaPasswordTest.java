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
public class GestoreModificaPasswordTest {

    /**
     * Test of esegui method, of class GestoreModificaPassword.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.cambiaPassword("vecchia", "nuova")).thenReturn(Boolean.TRUE);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);

        GestoreModificaPassword g = new GestoreModificaPassword();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        g.setMittente(c);
        g.setVecchia("vecchia");
        g.setNuova("nuova");
        g.esegui();

        verify(n).setOriginale("ModificaPassword");
        verify(n).setRiuscita(true);
        verify(c).send("not");
    }

    /**
     * Test of esegui method, of class GestoreModificaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.cambiaPassword("vecchia", "nuova")).thenReturn(Boolean.FALSE);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        GestoreModificaPassword g = new GestoreModificaPassword();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setMittente(c);
        g.setVecchia("vecchia");
        g.setNuova("nuova");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreModificaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreModificaPassword g = new GestoreModificaPassword();
//manca vecchia
        g.setNuova("nuova");
        g.esegui();
    }
    /**
     * Test of esegui method, of class GestoreModificaPassword.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr2() throws Exception {
        GestoreModificaPassword g = new GestoreModificaPassword();
        g.setVecchia("vecchia");
        //manca nuova
        g.esegui();
    }
}