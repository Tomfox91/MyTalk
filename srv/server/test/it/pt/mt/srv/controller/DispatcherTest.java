package it.pt.mt.srv.controller;

import it.pt.mt.srv.controller.gestori.GestoreLogout;
import it.pt.mt.srv.controller.network.IConnessione;
import it.pt.mt.srv.controller.notifiche.FactoryNotifiche;
import it.pt.mt.srv.controller.notifiche.NotificaOperazione;
import java.util.Observable;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FactoryNotifiche.class, RegistroConnessioni.class, Dispatcher.class})
public class DispatcherTest {

    private IConnessione conn;

    /**
     * Test of update method, of class Dispatcher.
     */
    @Test
    public void testUpdateOK() {
        Dispatcher d = Dispatcher.getInstance();
        assertNotNull(d);
        assertEquals(d, Dispatcher.getInstance());

        conn = mock(IConnessione.class);
        Observable sub = new fintoObservable();

        d.update(sub, "{\"tipo\":\"EventoFinto\",\"evento\":{\"campo1\":\"pippo\",\"campo2\":42}}");

        verify(conn).send("pippo 42");

        conn = mock(IConnessione.class);
        when(conn.getEmail()).thenReturn("a@b.com");
        d.update(sub, "{\"tipo\":\"AltroEventoFinto\",\"evento\":{\"lista\":[\"cane\",\"casa\",\"pippo\",\"paperino\"],\"pluto\":3.1415916}}");
        verify(conn).send("cane casa pippo paperino 3.1415916");
    }

    /**
     * Test of update method, of class Dispatcher.
     */
    @Test
    public void testUpdateErrors() {
        Dispatcher d = Dispatcher.getInstance();

        NotificaOperazione n = mock(NotificaOperazione.class);
        when(n.serializza()).thenReturn("errstring");

        FactoryNotifiche f = mock(FactoryNotifiche.class);
        when(f.creaNotificaOperazione()).thenReturn(n);

        mockStatic(FactoryNotifiche.class);
        when(FactoryNotifiche.getInstance()).thenReturn(f);

        Observable sub = new fintoObservable();

        // testo a caso
        conn = mock(IConnessione.class);
        d.update(sub, "cicciabubu");
        verify(conn).send("errstring");

        //senza membri obbligatori
        conn = mock(IConnessione.class);
        d.update(sub, "{}");
        verify(conn).send("errstring");

        //senza tipo
        conn = mock(IConnessione.class);
        d.update(sub, "{\"evento\":{\"campo1\":\"pippo\",\"campo2\":42}}");
        verify(conn).send("errstring");

        //con tipo vuoto
        conn = mock(IConnessione.class);
        d.update(sub, "{\"tipo\":\"\",\"evento\":{\"campo1\":\"pippo\",\"campo2\":42}}");
        verify(conn).send("errstring");

        //senza evento
        conn = mock(IConnessione.class);
        d.update(sub, "{\"tipo\":\"EventoFinto\"}");
        verify(conn).send("errstring");

        //evento ip con connessione registrata
        conn = mock(IConnessione.class);
        when(conn.getEmail()).thenReturn("a@b.com");
        d.update(sub, "{\"tipo\":\"EventoFinto\",\"evento\":{\"campo1\":\"pippo\",\"campo2\":42}}");
        verify(conn).send("errstring");

        //evento registrato con connessione ip
        conn = mock(IConnessione.class);
        d.update(sub, "{\"tipo\":\"AltroEventoFinto\",\"evento\":{\"lista\":[\"cane\",\"casa\",\"pippo\",\"paperino\"],\"pluto\":3.1415916}}");
        verify(conn).send("errstring");
    }

    /**
     * Test - chiusura connessione IP.
     */
    @Test
    public void testUpdateNull0() {
        Dispatcher d = Dispatcher.getInstance();

        conn = mock(IConnessione.class);
        when(conn.getIP()).thenReturn("1.2.3.4");
        Observable sub = new fintoObservable();
        RegistroConnessioni rcon = mock(RegistroConnessioni.class);

        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);

        d.update(sub, null);

        verify(rcon).rimuoviConnessione("1.2.3.4");
    }

    /**
     * Test - chiusura connessione Email.
     */
    @Test
    public void testUpdateNull1() throws Exception {
        Dispatcher d = Dispatcher.getInstance();

        conn = mock(IConnessione.class);
        when(conn.getIP()).thenReturn("1.2.3.4");
        when(conn.getEmail()).thenReturn("a@b.com");
        Observable sub = new fintoObservable();
        RegistroConnessioni rcon = mock(RegistroConnessioni.class);

        mockStatic(RegistroConnessioni.class);
        when(RegistroConnessioni.getInstance()).thenReturn(rcon);

        GestoreLogout glog = mock(GestoreLogout.class);
        whenNew(GestoreLogout.class).withNoArguments().thenReturn(glog);

        d.update(sub, null);

        verify(rcon).rimuoviConnessione("1.2.3.4");
        verify(glog).esegui();
    }

    /**
     * Test of update method, of class Dispatcher.
     */
    @Test(expected = AssertionError.class)
    public void testUpdateErr0() {
        Dispatcher.getInstance().update(new Observable(),
                "{\"tipo\":\"EventoFinto\",\"evento\":{\"campo1\":\"pippo\",\"campo2\":42}}");
    }

    /**
     * Test of update method, of class Dispatcher.
     */
    @Test(expected = AssertionError.class)
    public void testUpdateErr1() {
        Dispatcher.getInstance().update(new fintoObservable(),
                new Object());
    }

    private class fintoObservable extends Observable
            implements IConnessione.ISubject {

        @Override
        public IConnessione getConnessione() {
            return conn;
        }
    }
}