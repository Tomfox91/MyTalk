package it.pt.mt.srv.controller.network;

import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaProprioIP;
import java.nio.CharBuffer;
import java.util.Observer;
import org.apache.catalina.websocket.WsOutbound;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FactoryNotifiche.class})
public class ConnessioneTest {
    /**
     * Test of getters and setters, of class Connessione.Subject.
     */
    @Test
    public void testGettersAndSetters() throws Exception {
        Connessione c = new Connessione("1.2.3.4");
        assertEquals("1.2.3.4", c.getIP());
        assertNull(c.getEmail());
        c.setEmail("io@me.com");
        assertEquals("io@me.com", c.getEmail());
    }

    /**
     * Test of send method, of class Connessione.
     */
    @Test
    public void testSend() throws Exception {
        WsOutbound wsout = mock(WsOutbound.class);
        Connessione c = new Connessione("1.2.3.4");
        Whitebox.setInternalState(c, "outbound", wsout);

        c.send("pippo");
        verify(wsout).writeTextMessage(any(CharBuffer.class));
    }

    /**
     * Test of onTextMessage method, of class Connessione.
     */
    @Test
    public void testOnTextMessage() throws Exception {
        Connessione c = new Connessione("1.2.3.4");
        CharBuffer buf = CharBuffer.wrap("pippo");

        Observer o = mock(Observer.class);
        c.getSubject().addObserver(o);

        c.onTextMessage(buf);

        verify(o).update(c.getSubject(), "pippo");
    }

    /**
     * Test of onOpen method, of class Connessione.
     */
    @Test
    public void testOnOpen() throws Exception {
        Connessione c = new Connessione("1.2.3.4");
        Connessione spia = spy(c);

        NotificaProprioIP nn =
                mock(NotificaProprioIP.class);
        when(nn.serializza()).thenReturn("not-ip");
        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaProprioIP()).thenReturn(nn);
        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);

        doNothing().when(spia).send(anyString());
        spia.onOpen(null);
        verify(spia).send("not-ip");
    }

    /**
     * Test of onClose method, of class Connessione.
     */
    @Test
    public void testOnClose() throws Exception {
        Connessione c = new Connessione("1.2.3.4");
        Observer o = mock(Observer.class);
        c.getSubject().addObserver(o);
        c.onClose(400);
        c.onClose(400);

        verify(o, times(1)).update(c.getSubject(), null);
    }

    /**
     * Test of getSubject.getConnessione, of class Connessione.Subject.
     */
    @Test
    public void testGetConnection() throws Exception {
        Connessione c = new Connessione("1.2.3.4");
        assertEquals(c,
                ((IConnessione.ISubject) c.getSubject()).getConnessione());
    }
}