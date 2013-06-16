package it.pt.mt.srv.controller.gestori;

import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaListaAggiungi;
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
public class GestoreAccettazioneAmiciziaTest {

    /**
     * Test of esegui method, of class GestoreAccettazioneAmicizia.
     */
    @Test
    public void testEsegui() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        when(u.getUserName()).thenReturn("username");
        when(u.getStato()).thenReturn("stuto");
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getEmail()).thenReturn("b@c.com");
        when(a.getUserName()).thenReturn("asername");
        when(a.getStato()).thenReturn("stato");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaListaAggiungi nn =
                mock(NotificaListaAggiungi.class);
        when(nn.serializza()).thenReturn("not-ric");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaAggiungi()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreAccettazioneAmicizia g = new GestoreAccettazioneAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.setAccettata(true);
        g.esegui();

        verify(a).accettaAmiciziaCon("a@b.com");
        verify(nn).setEmail("a@b.com");
        verify(nn).setUsername("asername");
        verify(nn).setStato("stuto");
        verify(c).send("not-ric");

        verify(nn).setEmail("b@c.com");
        verify(nn).setUsername("username");
        verify(nn).setStato("stato");
        verify(ca).send("not-ric");
    }

    /**
     * Test of esegui method, of class GestoreAccettazioneAmicizia.
     */
    @Test
    public void testEseguiRifiuto() throws Exception {
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

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreAccettazioneAmicizia g = new GestoreAccettazioneAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.setAccettata(false);
        g.esegui();

        verify(a).rifiutaAmiciziaCon("a@b.com");
    }

    /**
     * Test of esegui method, of class GestoreAccettazioneAmicizia.
     */
    @Test
    public void testEseguiAltroOffline() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteRegistrato u = mock(IUtenteRegistrato.class);
        when(u.getEmail()).thenReturn("a@b.com");
        when(u.getUserName()).thenReturn("username");
        when(u.getStato()).thenReturn("stuto");
        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getEmail()).thenReturn("b@c.com");
        when(a.getUserName()).thenReturn("asername");
        when(a.getStato()).thenReturn("stato");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("a@b.com")).thenReturn(u);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaListaAggiungi nn =
                mock(NotificaListaAggiungi.class);
        when(nn.serializza()).thenReturn("not-ric");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaListaAggiungi()).thenReturn(nn);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);

        GestoreAccettazioneAmicizia g = new GestoreAccettazioneAmicizia();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setEmail("b@c.com");
        g.setAccettata(true);
        g.esegui();

        verify(a).accettaAmiciziaCon("a@b.com");
        verify(nn).setEmail("a@b.com");
        verify(nn).setUsername("asername");
        verify(nn).setStato("stuto");
        verify(c).send("not-ric");
    }

    /**
     * Test of esegui method, of class GestoreNuovaAmicizia.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        GestoreAccettazioneAmicizia g = new GestoreAccettazioneAmicizia();
        //manca email
        g.setAccettata(true);
        g.esegui();
    }
}