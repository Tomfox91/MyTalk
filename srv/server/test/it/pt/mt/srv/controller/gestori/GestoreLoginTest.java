package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaListaAggiungi;
import it.pt.mt.srv.controller.notifiche.NotificaListaModifica;
import it.pt.mt.srv.controller.notifiche.NotificaNuovaRichiestaAmicizia;
import it.pt.mt.srv.controller.notifiche.NotificaNuovoMessaggio;
import it.pt.mt.srv.controller.notifiche.NotificaProprioUtente;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IMessaggio;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class,
    RegistroConnessioni.class})
public class GestoreLoginTest {

    /**
     * Test of esegui method, of class GestoreLogin.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        when(u.checkPassword("password")).thenReturn(Boolean.TRUE);
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        List<IUtenteRegistrato> aa =
                new ArrayList<IUtenteRegistrato>(Arrays.asList(a));
        IMessaggio m = mock(IMessaggio.class);
        when(m.getMittente()).thenReturn("mitt");
        List<IMessaggio> mm =
                new ArrayList<IMessaggio>(Arrays.asList(m));
        when(u.getAmici()).thenReturn(aa);
        when(u.getAmiciziePendenti()).thenReturn(aa);
        when(u.getMessaggi()).thenReturn(mm);
        when(a.getEmail()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        NotificaProprioUtente np = mock(NotificaProprioUtente.class);
        when(np.serializza()).thenReturn("not-pro");
        NotificaListaAggiungi na = mock(NotificaListaAggiungi.class);
        when(na.serializza()).thenReturn("not-agg");
        NotificaNuovaRichiestaAmicizia nr =
                mock(NotificaNuovaRichiestaAmicizia.class);
        when(nr.serializza()).thenReturn("not-ric");
        NotificaNuovoMessaggio nm = mock(NotificaNuovoMessaggio.class);
        when(nm.serializza()).thenReturn("not-mes");
        NotificaListaModifica ne = mock(NotificaListaModifica.class);
        when(ne.serializza()).thenReturn("not-mod");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaProprioUtente()).thenReturn(np);
        when(f.creaNotificaListaAggiungi()).thenReturn(na);
        when(f.creaNotificaNuovaRichiestaAmicizia()).thenReturn(nr);
        when(f.creaNotificaNuovoMessaggio()).thenReturn(nm);
        when(f.creaNotificaListaModifica()).thenReturn(ne);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreLogin g = new GestoreLogin();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.esegui();

        verify(rcon).aggiungiConnessione("a@b.com", c);

        verify(c).setEmail("a@b.com");
        verify(u).setStato("Online");

        verify(np).setEmail("a@b.com");
        verify(c).send("not-pro");

        verify(na).setEmail("b@c.com");
        verify(c).send("not-agg");

        verify(nr).setEmail("b@c.com");
        verify(c).send("not-ric");

        verify(nm).setMittente("mitt");
        verify(c).send("not-mes");

        verify(ne).setEmail("a@b.com");
        verify(ne).setStato("Online");
        verify(ca).send("not-mod");
    }

    /**
     * Test of esegui method, of class GestoreLogin.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.checkPassword("password")).thenReturn(Boolean.FALSE);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);

        GestoreLogin g = new GestoreLogin();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreLogin.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com"))
                .thenThrow(new ConditionException());

        GestoreLogin g = new GestoreLogin();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        g.setEmail("a@b.com");
        g.setPassword("password");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreLogin.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr2() throws Exception {
        GestoreLogin g = new GestoreLogin();
        g.setEmail("a@b.com");
        //manca password
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreLogin.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr3() throws Exception {
        GestoreLogin g = new GestoreLogin();
        //manca email
        g.setPassword("password");
        g.esegui();
    }
}