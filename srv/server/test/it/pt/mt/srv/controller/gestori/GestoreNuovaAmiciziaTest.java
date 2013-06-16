package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaNuovaRichiestaAmicizia;
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
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class,
    RegistroConnessioni.class})
public class GestoreNuovaAmiciziaTest {

    /**
     * Test of esegui method, of class GestoreNuovaAmicizia.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        when(u.getUserName()).thenReturn("username");
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("not-op");
        NotificaNuovaRichiestaAmicizia nn =
                mock(NotificaNuovaRichiestaAmicizia.class);
        when(nn.serializza()).thenReturn("not-ric");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);
        when(f.creaNotificaNuovaRichiestaAmicizia()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreNuovaAmicizia g = new GestoreNuovaAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.esegui();

        verify(a).registraRichiestaAmiciziaDa("a@b.com");
        verify(n).setOriginale("NuovaAmicizia");
        verify(n).setRiuscita(true);
        verify(c).send("not-op");

        verify(nn).setEmail("a@b.com");
        verify(nn).setUsername("username");
        verify(ca).send("not-ric");
    }

    /**
     * Test of esegui method, of class GestoreNuovaAmicizia.
     */
    @Test
    public void testEseguiAltroOffline() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        when(u.getUserName()).thenReturn("username");
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("not-op");
        NotificaNuovaRichiestaAmicizia nn =
                mock(NotificaNuovaRichiestaAmicizia.class);
        when(nn.serializza()).thenReturn("not-ric");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);
        when(f.creaNotificaNuovaRichiestaAmicizia()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);

        GestoreNuovaAmicizia g = new GestoreNuovaAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.esegui();

        verify(a).registraRichiestaAmiciziaDa("a@b.com");
        verify(n).setOriginale("NuovaAmicizia");
        verify(n).setRiuscita(true);
        verify(c).send("not-op");
    }

    /**
     * Test of esegui method, of class GestoreNuovaAmicizia.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr() throws Exception {
        GestoreNuovaAmicizia g = new GestoreNuovaAmicizia();
        //manca email
        g.esegui();
    }
}