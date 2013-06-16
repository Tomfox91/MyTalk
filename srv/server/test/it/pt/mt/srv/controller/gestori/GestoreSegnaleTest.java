package it.pt.mt.srv.controller.gestori;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;
import it.pt.mt.srv.controller.RegistroConnessioni;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaSegnale;
import it.pt.mt.srv.model.GestoreUtenti;
import it.pt.mt.srv.model.IUtenteIP;
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
public class GestoreSegnaleTest {

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test
    public void testEseguiEmailEmail() throws Exception
    {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getNome()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaSegnale n = mock(NotificaSegnale.class);
        when(n.serializza()).thenReturn("not-inv");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaSegnale()).thenReturn(n);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreSegnale g = new GestoreSegnale();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setDestinatario("b@c.com");
        g.setSottotipo("tipo");
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        g.esegui();

        verify(n).setMittente("a@b.com");
        verify(n).setSegnale(s);
        verify(n).setSottotipo("tipo");
        verify(ca).send("not-inv");
    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test
    public void testEseguiIPEmail() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getIP()).thenReturn("5.6.7.8");
        IConnessione ca = mock(IConnessione.class);

        IUtenteRegistrato a = mock(IUtenteRegistrato.class);
        when(a.getNome()).thenReturn("b@c.com");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteRegistrato("b@c.com")).thenReturn(a);

        NotificaSegnale n = mock(NotificaSegnale.class);
        when(n.serializza()).thenReturn("not-inv");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaSegnale()).thenReturn(n);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("b@c.com")).thenReturn(ca);

        GestoreSegnale g = new GestoreSegnale();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setDestinatario("b@c.com");
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        g.setSottotipo("tipo");
        g.esegui();

        verify(n).setMittente("5.6.7.8");
        verify(n).setSegnale(s);
        verify(n).setSottotipo("tipo");
        verify(ca).send("not-inv");

    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test
    public void testEseguiEmailIP() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");
        IConnessione ca = mock(IConnessione.class);

        IUtenteIP a = mock(IUtenteIP.class);
        when(a.getNome()).thenReturn("1.2.3.4");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteIP("1.2.3.4")).thenReturn(a);

        NotificaSegnale n = mock(NotificaSegnale.class);
        when(n.serializza()).thenReturn("not-inv");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaSegnale()).thenReturn(n);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("1.2.3.4")).thenReturn(ca);

        GestoreSegnale g = new GestoreSegnale();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setDestinatario("1.2.3.4");
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        g.setSottotipo("tipo");
        g.esegui();

        verify(n).setMittente("a@b.com");
        verify(n).setSegnale(s);
        verify(n).setSottotipo("tipo");
        verify(ca).send("not-inv");
    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiEmailIPErr() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getEmail()).thenReturn("a@b.com");

        IUtenteIP a = mock(IUtenteIP.class);
        when(a.getNome()).thenReturn("1.2.3.4");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteIP("1.2.3.4")).thenReturn(a);

        NotificaSegnale n = mock(NotificaSegnale.class);
        when(n.serializza()).thenReturn("not-inv");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaSegnale()).thenReturn(n);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        //altro non connesso

        GestoreSegnale g = new GestoreSegnale();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setDestinatario("1.2.3.4");
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        g.setSottotipo("tipo");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test
    public void testEseguiIPIP() throws Exception {
        IConnessione c = mock(IConnessione.class);
        when(c.getIP()).thenReturn("5.6.7.8");
        IConnessione ca = mock(IConnessione.class);

        IUtenteIP a = mock(IUtenteIP.class);
        when(a.getNome()).thenReturn("1.2.3.4");

        GestoreUtenti gut = mock(GestoreUtenti.class);
        when(gut.getUtenteIP("1.2.3.4")).thenReturn(a);

        NotificaSegnale ni = mock(NotificaSegnale.class);
        when(ni.serializza()).thenReturn("not-inv");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaSegnale()).thenReturn(ni);

        RegistroConnessioni rcon = mock(RegistroConnessioni.class);
        when(rcon.getConnessione("1.2.3.4")).thenReturn(ca);

        GestoreSegnale g = new GestoreSegnale();
        mockStatic(GestoreUtenti.class);
        when(GestoreUtenti.getInstance()).thenReturn(gut);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);
        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);
        g.setMittente(c);
        g.setDestinatario("1.2.3.4");
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        g.setSottotipo("tipo");
        g.esegui();

        verify(ni).setMittente("5.6.7.8");
        verify(ni).setSegnale(s);
        verify(ni).setSottotipo("tipo");
        verify(ca).send("not-inv");
    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr0() throws Exception {
        GestoreSegnale g = new GestoreSegnale();
        //manca destinatario
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        g.setSottotipo("tipo");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr1() throws Exception {
        GestoreSegnale g = new GestoreSegnale();
        g.setDestinatario("b@c.com");
        //manca segnale
        g.setSottotipo("tipo");
        g.esegui();
    }

    /**
     * Test of esegui method, of class GestoreSegnale.
     */
    @Test(expected = ConditionException.class)
    public void testEseguiErr2() throws Exception {
        GestoreSegnale g = new GestoreSegnale();
        g.setDestinatario("b@c.com");
        TextNode s = JsonNodeFactory.instance.textNode("@@@@@");
        g.setSegnale(s);
        //manca sottotipo
        g.esegui();
    }
}