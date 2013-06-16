package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaListaElimina;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteRegistrato;
import it.pt.mt.srv.util.ConditionException;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({GestoreUtenti.class, FactoryNotifiche.class,
    RegistroConnessioni.class})
public class GestoreEliminaAmiciziaTest {

    /**
     * Test of esegui method, of class GestoreEliminaAmicizia.
     */
    @Test
    public void testEsegui0() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getEmail()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaListaElimina nn =
                mock(NotificaListaElimina.class);
        when(nn.serializza()).thenReturn("not-elm");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaElimina()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreEliminaAmicizia g = new GestoreEliminaAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.esegui();

        verify(u).cancellaAmiciziaCon("b@c.com");
        verify(nn).setEmail("a@b.com");
        verify(c).send("not-elm");

        verify(nn).setEmail("b@c.com");
        verify(ca).send("not-elm");
    }

    /**
     * Test of esegui method, of class GestoreEliminaAmicizia.
     */
    @Test
    public void testEsegui1() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getEmail()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaListaElimina nn =
                mock(NotificaListaElimina.class);
        when(nn.serializza()).thenReturn("not-elm");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaElimina()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(null); //altro offline

        GestoreEliminaAmicizia g = new GestoreEliminaAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.esegui();

        verify(u).cancellaAmiciziaCon("b@c.com");
        verify(nn).setEmail("a@b.com");
        verify(c).send("not-elm");
    }

    /**
     * Test of esegui method, of class GestoreEliminaAmicizia.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        doThrow(new ConditionException()).when(u).cancellaAmiciziaCon(anyString());
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getEmail()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaListaElimina nn =
                mock(NotificaListaElimina.class);
        when(nn.serializza()).thenReturn("not-elm");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaElimina()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreEliminaAmicizia g = new GestoreEliminaAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreEliminaAmicizia.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreEliminaAmicizia g = new GestoreEliminaAmicizia();
        g.esegui();
    }
}