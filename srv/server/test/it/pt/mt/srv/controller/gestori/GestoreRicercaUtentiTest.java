package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaEsitiRicerca;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.mockito.Mockito.verify;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class})
public class GestoreRicercaUtentiTest {

    /**
     * Test of esegui method, of class GestoreRicercaUtenti.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione conn = mock(IConnessione.class);
        when(conn.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getEmail()).thenReturn("b@c.com");
        when(a.getUserName()).thenReturn("Nome Cognome");

        IUtenteRegistrato b = mock(IUtenteRegistrato.class);
        when(b.getEmail()).thenReturn("e@c.com");
        when(b.getUserName()).thenReturn("Nome Eognome");

        IUtenteRegistrato c = mock(IUtenteRegistrato.class);
        when(c.getEmail()).thenReturn("f@c.com");
        when(c.getUserName()).thenReturn("Nome Fognome");

        List<IUtenteRegistrato> at = (Arrays.asList(a, b, c, u));
        List<IUtenteRegistrato> aa = (Arrays.asList(b));
        List<IUtenteRegistrato> ap = (Arrays.asList(c));
        when(u.getAmici()).thenReturn(aa);
        when(u.getAmiciziePendenti()).thenReturn(ap);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.ricercaUtenti("pattern")).thenReturn(at);

        NotificaEsitiRicerca n = mock(NotificaEsitiRicerca.class);
        when(n.serializza()).thenReturn("not");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaEsitiRicerca()).thenReturn(n);

        GestoreRicercaUtenti g = new GestoreRicercaUtenti();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        g.setMittente(conn);
        g.setPattern("pattern");
        g.esegui();

        Map<String, String> l = new HashMap<String, String>(1);
        l.put("b@c.com", "Nome Cognome");
        verify(n).setMappa(l);
        verify(conn).send("not");
    }

    /**
     * Test of esegui method, of class GestoreRicercaUtenti.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr() throws Exception {
        GestoreRicercaUtenti g = new GestoreRicercaUtenti();
        //manca pattern
        g.esegui();
    }
}